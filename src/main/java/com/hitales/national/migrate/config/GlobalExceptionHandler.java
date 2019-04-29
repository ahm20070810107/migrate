package com.hitales.national.migrate.config;

import com.hitales.commons.error.BizException;
import com.hitales.national.migrate.enums.GlobalHttpStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * /**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-01-24
 * @time:19:28
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * ConstraintViolationException.class,
	 * HttpRequestMethodNotSupportedException.class,
	 * HttpMediaTypeNotSupportedException.class,
	 * HttpMediaTypeNotAcceptableException.class,
	 * MissingPathVariableException .class,
	 * MissingServletRequestParameterException .class,
	 * ServletRequestBindingException.class,
	 * ConversionNotSupportedException.class,
	 * TypeMismatchException.class,
	 * HttpMessageNotReadableException.class,
	 * HttpMessageNotWritableException.class,
	 * MethodArgumentNotValidException.class,
	 * MissingServletRequestPartException.class,
	 * BindException.class,
	 * NoHandlerFoundException.class,
	 * AsyncRequestTimeoutException.class,
	 */
	@ExceptionHandler(value = {BizException.class, Exception.class})
	@ResponseBody
	public ResultWrapper bizErrorHandler(HttpServletRequest req, Exception e, HttpServletResponse response) {
		HttpStatusResult httpStatusResult = new HttpStatusResult();
		handleExceptionStatus(e, httpStatusResult);
		//运维需要将业务错误和系统内部错误区分打印
		printErrorInfo(e, httpStatusResult);
		ResultWrapper wrapper = new ResultWrapper();
		wrapper.setTimestamp(System.currentTimeMillis());
		wrapper.setStatus(httpStatusResult.getHttpCode());
		wrapper.setError(httpStatusResult.getReasonPhrase());
		wrapper.setMessage(httpStatusResult.getMessage());
		wrapper.setPath(req.getRequestURI());

		response.setStatus(httpStatusResult.getHttpCode());
		return wrapper;
	}

	private void printErrorInfo(Exception e, HttpStatusResult httpStatusResult) {
		StringBuilder sb = new StringBuilder();
		//系统错误
		if (httpStatusResult.getType().equals(ExceptionType.INTERNAL)) {
			// 系统错误不特别区分，与普通错误一样
//			sb.append("SystemError: [");
		}
		//业务错误
		if (httpStatusResult.getType().equals(ExceptionType.BIZ) || httpStatusResult.getType().equals(ExceptionType.SPRING)) {
			sb.append("ServiceError: ");
		}
		// biz 异常的message肯定不为null
		String msg = Strings.isBlank(e.getMessage()) ? e.toString() : e.getMessage();
		sb.append("[ ").append(msg).append("]\n");
        sb.append(e.toString()).append("\n");
		// 打堆栈信息
		ThrowablePrint throwablePrint = new ThrowablePrint(e);
		throwablePrint.getStackTrace(sb);
		log.error(sb.toString());
	}

	/**
	 * 给不同的异常设置不同的httpStatus和给前台的message
	 *
	 * @param ex               异常
	 * @param httpStatusResult 结果
	 */
	private void handleExceptionStatus(Exception ex, HttpStatusResult httpStatusResult) {
		httpStatusResult.setType(ExceptionType.SPRING);

		if (ex instanceof HttpRequestMethodNotSupportedException) {
			fillHttpStatusResult(httpStatusResult,"不支持请求的方法！", ExceptionType.SPRING, HttpStatus.METHOD_NOT_ALLOWED);
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			fillHttpStatusResult(httpStatusResult,"请求头的媒体类型不支持！", ExceptionType.SPRING, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			fillHttpStatusResult(httpStatusResult,"请求头的媒体类型不可接受！", ExceptionType.SPRING, HttpStatus.NOT_ACCEPTABLE);
		} else if (ex instanceof ConversionNotSupportedException) {
			fillHttpStatusResult(httpStatusResult,"转换bean时参数不正确！", ExceptionType.SPRING, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//  MissingServletRequestParameterException、 MissingPathVariableException都继承自ServletRequestBindingException
		else if (ex instanceof ServletRequestBindingException || ex instanceof HttpMessageNotReadableException || ex instanceof BindException) {
			fillHttpStatusResult(httpStatusResult,"绑定请求参数出错！", ExceptionType.SPRING, HttpStatus.BAD_REQUEST);
		} else if (ex instanceof TypeMismatchException) {
			fillHttpStatusResult(httpStatusResult,"请求参数类型不匹配！", ExceptionType.SPRING, HttpStatus.BAD_REQUEST);
		} else if (ex instanceof HttpMessageNotWritableException) {
			fillHttpStatusResult(httpStatusResult,"http消息不可写！", ExceptionType.SPRING, HttpStatus.INTERNAL_SERVER_ERROR);

		} else if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException m = (ConstraintViolationException) ex;
			String msg = m.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
			fillHttpStatusResult(httpStatusResult,msg, ExceptionType.SPRING, HttpStatus.BAD_REQUEST);
		} else if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException m = (MethodArgumentNotValidException) ex;
			String msg = m.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
			fillHttpStatusResult(httpStatusResult,msg, ExceptionType.SPRING, HttpStatus.BAD_REQUEST);

		} else if (ex instanceof MissingServletRequestPartException) {
			fillHttpStatusResult(httpStatusResult,"不支持或者未传入multipart/form-data！", ExceptionType.SPRING, HttpStatus.BAD_REQUEST);

		} else if (ex instanceof NoHandlerFoundException) {
			fillHttpStatusResult(httpStatusResult,"请求分发器找不到对应的处理器来处理请求！", ExceptionType.SPRING, HttpStatus.NOT_FOUND);

		} else if (ex instanceof AsyncRequestTimeoutException) {
			fillHttpStatusResult(httpStatusResult,"请求超时！", ExceptionType.SPRING, HttpStatus.SERVICE_UNAVAILABLE);
			// 处理登录验证的异常
		}else if (ex instanceof AuthenticationException) {
			if (ex.getCause() instanceof DisabledException) {
				fillHttpStatusResult(httpStatusResult,ex.getMessage(), ExceptionType.SPRING, HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (ex instanceof InternalAuthenticationServiceException || ex instanceof BadCredentialsException
					|| ex instanceof UsernameNotFoundException) {
				fillHttpStatusResult(httpStatusResult,"用户名或密码错误！", ExceptionType.SPRING, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				fillHttpStatusResult(httpStatusResult,"无效登录！", ExceptionType.SPRING, HttpStatus.UNAUTHORIZED);
			}
		} else if (ex instanceof BizException) {
			fillHttpStatusResult(httpStatusResult,ex.getMessage(), ExceptionType.BIZ, GlobalHttpStatus.GLOBAL_BIZ_STATUS);
		}
		// 统一处理成内部错误
		else {
			fillHttpStatusResult(httpStatusResult,"服务器内部错误！", ExceptionType.INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void fillHttpStatusResult(HttpStatusResult httpStatusResult, String msg ,ExceptionType exceptionType, Object httpStatus){
		httpStatusResult.setMessage(msg);
		httpStatusResult.setType(exceptionType);
		if(httpStatus instanceof HttpStatus) {
			HttpStatus status = (HttpStatus) httpStatus;
			httpStatusResult.setHttpCode(status.value());
			httpStatusResult.setReasonPhrase(status.getReasonPhrase());
		}else if(httpStatus instanceof GlobalHttpStatus){
			GlobalHttpStatus status = (GlobalHttpStatus) httpStatus;
			httpStatusResult.setHttpCode(status.value());
			httpStatusResult.setReasonPhrase(status.getReasonPhrase());
		}
	}

	@Data
	private static class HttpStatusResult {
		private Integer httpCode;

		private String reasonPhrase;
		/**
		 * 错误类型
		 */
		private ExceptionType type;

		private Integer internalErrorCode;

		private String message;
	}

	enum ExceptionType {
		/**
		 * 业务错误
		 */
		BIZ,
		/**
		 * 内部错误
		 */
		INTERNAL,
		/**
		 * spring架构错误，如请求参数不对，请求url不存在等
		 */
		SPRING
	}

	@Data
	private static class ResultWrapper {
		private Long timestamp;
		private Integer status;
		private String error;
		private String message;
		private String path;
	}


	/**
	 *  @see Throwable
	 */
	private class ThrowablePrint {

		/** Caption  for labeling causative exception stack traces */
		private static final String CAUSE_CAPTION = "Caused by: ";

		/** Caption for labeling suppressed exception stack traces */
		private static final String SUPPRESSED_CAPTION = "Suppressed: ";

        private Throwable throwable;
        ThrowablePrint(Throwable throwable){
        	this.throwable = throwable;
		}

		private void getStackTrace(StringBuilder sb) {

			Set<Throwable> dejaVu = Collections.newSetFromMap(new IdentityHashMap<>());
			dejaVu.add(throwable);
				// Print our stack trace
			   sb.append(this).append("\n");
				StackTraceElement[] trace = throwable.getStackTrace();
				for (StackTraceElement traceElement : trace)
					sb.append("\tat ").append(traceElement).append("\n");

				// Print suppressed exceptions, if any
				for (Throwable se : throwable.getSuppressed())
					printEnclosedStackTrace(se, trace, SUPPRESSED_CAPTION, "\t", dejaVu,sb);

				// Print cause, if any
				Throwable ourCause = throwable.getCause();
				if (ourCause != null)
					printEnclosedStackTrace(ourCause, trace, CAUSE_CAPTION, "", dejaVu,sb);

		}

		/**
		 * Print our stack trace as an enclosed exception for the specified
		 * stack trace.
		 */
		private void printEnclosedStackTrace(Throwable se ,StackTraceElement[] enclosingTrace,
											 String caption,
											 String prefix,
											 Set<Throwable> dejaVu,StringBuilder sb) {
			if (dejaVu.contains(se)) {
				sb.append("\t[CIRCULAR REFERENCE:").append(this).append("]\n");
			} else {
				dejaVu.add(se);
				// Compute number of frames in common between this and enclosing trace
				StackTraceElement[] trace = se.getStackTrace();
				int m = trace.length - 1;
				int n = enclosingTrace.length - 1;
				while (m >= 0 && n >=0 && trace[m].equals(enclosingTrace[n])) {
					m--; n--;
				}
				int framesInCommon = trace.length - 1 - m;

				// Print our stack trace
				sb.append(prefix).append( caption).append(this).append("\n");
				for (int i = 0; i <= m; i++)
					sb.append(prefix).append("\tat ").append(trace[i]).append("\n");
				if (framesInCommon != 0)
					sb.append(prefix).append( "\t... ").append(framesInCommon).append(" more").append("\n");

				// Print suppressed exceptions, if any
				for (Throwable thr : se.getSuppressed())
					printEnclosedStackTrace(thr, trace, SUPPRESSED_CAPTION, prefix +"\t", dejaVu,sb);

				// Print cause, if any
				Throwable ourCause = se.getCause();
				if (ourCause != null)
					printEnclosedStackTrace(ourCause, trace, CAUSE_CAPTION, prefix, dejaVu,sb);
			}
		}
	}
}
