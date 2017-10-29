package com.inpowered.service.api.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inpowered.service.api.dto.model.PageAnalysisDto;
import com.inpowered.service.api.dto.model.UrlDto;
import com.inpowered.service.api.service.UrlAnalysisService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/data")
@Api(value = "/data", description = "Operations on provide urls")
public class UrlResource {

	private static final Logger logger = LoggerFactory.getLogger(UrlResource.class);

	@Inject
	UrlAnalysisService analysisService;

	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@ApiOperation(value = "Parses and analyses provided url", notes = "Parses and analyses using aylien api", response = PageAnalysisDto.class)
	@ApiResponses({ @ApiResponse(code = 404, message = "Some error occured") })
	public Response analyseUrl(UrlDto url) {
		return Response.ok(this.analysisService.analyseUrl(url)).build();
	}

	@Produces({ MediaType.APPLICATION_JSON })
	//@Path("/{url}")
	@GET
	@ApiOperation(value = "Retrieve data for url that was already analysed", response = PageAnalysisDto.class)
	@ApiResponses({ @ApiResponse(code = 404, message = "Page has not been previously analysed") })
	public Response findAnalysisByUrl(@ApiParam(value = "Url to lookup", required = true) @QueryParam("url") final String url) {
		
		PageAnalysisDto result = this.analysisService.findAnalysisByUrl(url);
		
		if(result != null)
			return Response.ok(result).build();
		
		return Response.ok().build();
	}
}
