package Gamestudio.Server.WebService;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Rating;
import Gamestudio.entity.Score;
import Gamestudio.service.CommentService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;

@Path("/rating")
public class RatingRestService {
	@Autowired
	private RatingService ratingService;
	
	@POST 
	@Consumes("application/json")
	public Response setRating(Rating rating) {
		ratingService.setRating(rating);
		return Response.ok().build();
	}
  

  //http://localhost:8080/rest/score/mines
    @GET
    @Path("/{game}")
    @Produces("application/json")
    public double getAverageRating(@PathParam("game") String game)  {
        return ratingService.getAverageRating(game);
    }




    @GET
    @Path("/{game}/{username}")
    @Produces("application/json")
public int getValue(@PathParam("game") String game,@PathParam("username") String username) {
	
	return ratingService.getValue(game, username);
}
}

