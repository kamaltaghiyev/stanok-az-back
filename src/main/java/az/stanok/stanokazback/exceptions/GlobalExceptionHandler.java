package az.stanok.stanokazback.exceptions;

import az.stanok.stanokazback.config.validation.JacksonPropertyNodeNameProvider;
import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.exceptions.core.CustomException;
import az.stanok.stanokazback.exceptions.core.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.spi.nodenameprovider.JavaBeanProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    private final Logger logger = LogManager.getLogger();
    @Autowired
    private JacksonPropertyNodeNameProvider propertyNodeNameProvider;

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiResponse<List<String>>> handleNotFoundException(HttpServletRequest request, BadCredentialsException ex) {
//        logger.error("BadCredentialsException {}\n", request.getRequestURI(), ex);
//
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(ApiResponse.failure(ex.getMessage(), "Неверный логин или пароль"));
//    }

    @ExceptionHandler({NotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ApiResponse<List<String>>> handleNotFoundException(HttpServletRequest request, RuntimeException ex) {
        logger.error("{} {}\n", ex.getClass().getSimpleName(), request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage(), "Ресурс не найден"));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleOtpException(HttpServletRequest request, CustomException ex) {
        logger.error("CustomException  {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), ex.getMessageLoc()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleValidationException(HttpServletRequest request, ValidationException ex) {
        logger.error("ValidationException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), "Ошибка валидации"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        logger.error("handleMissingServletRequestParameterException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), "Не все параметры запроса переданы"));
    }

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<ApiResponse<List<Map<String, String>>>> handleMethodArgumentTypeMismatchException(
//            HttpServletRequest request, MethodArgumentTypeMismatchException ex
//    ) {
//        logger.error("handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex);
//
////        Map<String, String> details = new HashMap<>();
////        details.put("paramName", ex.getMethodParameter().getParameterName());
////        details.put("paramValue", Optional.ofNullable(ex)
////                .map(MethodArgumentTypeMismatchException::getMethodParameter)
////                .map(MethodParameter::getParameter)
////                .map(Parameter::toString)
////                .map(Object::toString)
////                .orElse(""));
////        details.put("errorMessage", ex.getMessage());
//        String paramName = ex.getMethodParameter().getParameterName();
//        Map<String, String> details = paramName != null ? Map.of(
//                paramName,
//                "Передан неверный тип аргумента"
//        ) : null;
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.failure(ex.getMessage(), "Ошибка валидации", details));
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        logger.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex);

//        List<Map<String, String>> details = new ArrayList<>();
//        ex.getBindingResult()
//                .getFieldErrors()
//                .forEach(fieldError -> {
//                    Map<String, String> detail = new HashMap<>();
//                    detail.put("objectName", fieldError.getObjectName());
//                    detail.put("field", fieldError.getField());
//                    detail.put("rejectedValue", "" + fieldError.getRejectedValue());
//                    detail.put("errorMessage", fieldError.getDefaultMessage());
//                    details.add(detail);
//                });
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    var jsonFieldName = getJsonFieldName(fieldError);
                    details.put(jsonFieldName, fieldError.getDefaultMessage());
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), "Ошибка валидации", details));
    }

    @ExceptionHandler(FileNameException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleFileNameException(HttpServletRequest request, FileNameException ex) {
        logger.error("handleMissingServletRequestParameterException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), "Sorry! Filename contains invalid path sequence!"));
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse<List<String>>> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
//        logger.error("handleAccessDeniedException {}\n", request.getRequestURI(), ex);
//
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body(ApiResponse.failure(ex.getMessage(), "Доступ запрещён"));
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<List<String>>> handleInternalServerError(HttpServletRequest request, Exception ex) {
        logger.error("handleInternalServerError {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(ex.getMessage(), "Внутренняя ошибка сервера. Обратитесь к администратору."));
    }

    private String getJsonFieldName(FieldError fieldError) {
        var cv = fieldError.unwrap(ConstraintViolationImpl.class);
        return propertyNodeNameProvider.getName(new JavaBeanPropertyImpl(cv.getRootBeanClass(), fieldError.getField()));
    }

    private static class JavaBeanPropertyImpl implements JavaBeanProperty {
        private final Class<?> declaringClass;
        private final String name;

        private JavaBeanPropertyImpl(Class<?> declaringClass, String name) {
            this.declaringClass = declaringClass;
            this.name = name;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return declaringClass;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
