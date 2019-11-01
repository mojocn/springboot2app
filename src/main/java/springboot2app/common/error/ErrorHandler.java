package springboot2app.common.error;

import springboot2app.common.util.MiscUtil;
import springboot2app.common.util.Result;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice //表明这是控制器的异常处理类
public class ErrorHandler {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * 输入参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result> NotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) throws Exception {
        BindingResult bindingResult = e.getBindingResult();
        //rfc4918 - 11.2. 422: Unprocessable Entity
        Result res = MiscUtil.getValidateError(bindingResult);
        return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = ExceptionBiz.class)
    public ResponseEntity<Result> handleBizException(HttpServletRequest req, ExceptionBiz e) throws Exception {
        Result res = new Result(400, e.getMessage());
        return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<Result> mysqlRowNotFound(HttpServletRequest req, EmptyResultDataAccessException e) throws Exception {
        Result res = new Result(422, e.getMessage());
        return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 数据库唯一键异常
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Result> DbUniqueKeyExceptionHandler(HttpServletRequest req, DataIntegrityViolationException e) throws Exception {
        Result res = new Result(422, e.getMessage());
        return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 登陆用户名错误
     */
    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    public ResponseEntity<Result> loginUserNameExceptionHandler(HttpServletRequest req, InternalAuthenticationServiceException e) throws Exception {
        Result res = new Result(200, "用户名错误");
        return new ResponseEntity<Result>(res, HttpStatus.OK);
    }

    /**
     * 404异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<Result> NoHandlerFoundExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        Result res = new Result(404, "接口不存在");
        return new ResponseEntity<Result>(res, HttpStatus.NOT_FOUND);
    }

    /**
     * 默认异常处理，前面未处理
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Result> defaultHandler(HttpServletRequest req, Exception e) throws Exception {
        Result res = new Result(500, e.getMessage());
        log.error("异常详情", e);
        return new ResponseEntity<Result>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}