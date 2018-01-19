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
import Gamestudio.entity.Favorite;
import Gamestudio.service.FavoriteService;

@Path("/favorite")
public class FavoriteRestService {
	@Autowired
	private FavoriteService favoriteService;
	
	@POST 
	@Consumes("application/json")
	public Response setFavorite(Favorite favorite) {
		favoriteService.setFavorite(favorite);
		return Response.ok().build();
	}
  
//	@GET
//	public  String hello() {		
//		return "Hello world";
//	}
  //http://localhost:8080/rest/score/mines
    @GET
    @Path("/{name}")
    @Produces("application/json")
    public List<Favorite> getFavoritesForName(@PathParam("name") String username)  {
        return favoriteService.getFavorite(username);
    }
}

