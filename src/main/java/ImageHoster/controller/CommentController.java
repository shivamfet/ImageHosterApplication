package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments" , method= RequestMethod.POST)
    public String addComment(@RequestParam("comment") String text , @PathVariable("imageId") Integer imageId , @PathVariable("imageTitle") String imageTitle,
                             Comment comment , HttpSession httpSession) {

        Image image = imageService.getImage(imageId);
        User user = (User)httpSession.getAttribute("loggeduser");
        comment.setText(text);
        comment.setDate(LocalDate.now());
        comment.setImage(image);
        comment.setUser(user);
        commentService.addComment(comment);
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
