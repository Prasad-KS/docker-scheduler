package org.paggarwal.dockerscheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.paggarwal.dockerscheduler.handlers.EmptyPayload;
import org.paggarwal.dockerscheduler.util.TriFunction;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by paggarwal on 2/22/16.
 */
public class RequestHandlerWrapper<V extends Validable> implements RequestHandler<V>,Route {

    private TypeReference<V> valueClass;
    private TriFunction<V,Map<String,String>,Map<String,String>,Answer> handler;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public RequestHandlerWrapper(TypeReference<V> valueClass, TriFunction<V,Map<String,String>,Map<String,String>,Answer> handler) {
        this.valueClass = valueClass;
        this.handler = handler;
    }


    public static String dataToJson(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    public final Answer process(V value, Map<String, String> urlParams, Map<String,String> headers) {
        if (value != null && !value.isValid()) {
            return new Answer(HTTP_BAD_REQUEST);
        } else {
            return handler.apply(value,urlParams, headers);
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            V value = null;
            if (valueClass != EmptyPayload.TYPE_REFERENCE) {
                value = objectMapper.readValue(request.body(), valueClass);
            }
            Map<String, String> urlParams = request.params();
            Map<String, String> headers = request.headers();
            Answer answer = process(value, urlParams, headers);
            response.status(answer.getCode());
            response.type("application/json");
            String body = dataToJson(answer.getBody());
            response.body(body);
            return body;
        } catch (JsonMappingException e) {
            response.status(400);
            response.body(e.getMessage());
            return e.getMessage();
        }
    }
}
