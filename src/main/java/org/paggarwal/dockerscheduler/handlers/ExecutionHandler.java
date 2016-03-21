package org.paggarwal.dockerscheduler.handlers;

import com.google.common.collect.ImmutableMap;
import org.paggarwal.dockerscheduler.Answer;
import org.paggarwal.dockerscheduler.Range;
import org.paggarwal.dockerscheduler.RequestHandlerWrapper;
import org.paggarwal.dockerscheduler.models.Execution;
import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.paggarwal.dockerscheduler.RequestHandlerWrapper.parseRange;

/**
 * Created by paggarwal on 3/7/16.
 */
@Service
public class ExecutionHandler {
    @Inject
    private ExecutionService executionService;

    public RequestHandlerWrapper<EmptyPayload> listExecutionsForATask() {
        return new RequestHandlerWrapper<EmptyPayload>(EmptyPayload.TYPE_REFERENCE,(value, urlParams, headers) -> {
            Range range = parseRange(headers);
            int count = executionService.count(Integer.parseInt(urlParams.get(":id")));
            List<Execution> results = executionService.list(Integer.parseInt(urlParams.get(":id")),parseRange(headers));
            Map<String,String> responseHeaders = ImmutableMap.of("Range-Unit",range.getUnits(),"Content-Range",range.getStart() + "-" + (results.size() + range.getStart()) + "/" + count);
            return new Answer(200,results,responseHeaders);
        });
    }
}
