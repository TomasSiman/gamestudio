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
import Gamestudio.entity.Score;
import Gamestudio.service.CommentService;
import Gamestudio.service.ScoreService;

@Path("/comment")
public class CommentRestService {
	@Autowired
	private CommentService commentService;
	
	@POST 
	@Consumes("application/json")
	public Response addComment(Comment comment) {
		commentService.addComment(comment);
		return Response.ok().build();
	}
  
//	@GET
//	public  String hello() {		
//		return "Hello world";
//	}
  //http://localhost:8080/rest/score/mines
    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Comment> getCommentsForGame(@PathParam("game") String game)  {
        return commentService.getComments(game);
    }
}

