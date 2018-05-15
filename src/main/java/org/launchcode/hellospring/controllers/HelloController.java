package org.launchcode.hellospring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

// controller and remote lesson

@Controller
//group routes by functionality
//RequestMapping at Controller level
/* example: ALL routes would live under /hello
if @RequestMapping("hello") */

public class HelloController {


    //specify route for controller
    @RequestMapping(value = "")
    // index method available at /
    // Use @ResponseBody when not using templates to return plain text
    @ResponseBody
    //adding "HttpServletRequest request" allows for queries
    //Spring calls method index the request
    //request is an object that represents the data from the http request the server received
    public String index(HttpServletRequest request) {

        //local userName variable will be the value that was passed in via the query string ?name=Chris
        //keys have to match
        //controller will get the data out of the request
        //will return null if bad request
        //add null check to safeguard
        String userName = request.getParameter("name");

        //null check, will create default value
        if (userName == null){
            userName = "World";
        }

        return "Hello " + userName;
    }

    //display a form that allows a sub-user to submit a request
    //GET method
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public String helloForm() {
        //build string to return HTML

        String html = "<form method='post'>" +
                "<input type='text' name='name' />" +
                "<select name='language'>" +
                "<option value='English'>English</option>" +
                "<option value='Spanish'>Spanish</option>" +
                "<option value='French'>French</option>" +
                "</select>" +
                "<input type='submit' value='Greet Me!' />" +
                "</form>";

        //POST request is sent to /hello
        //action param left off of form (can customize url in action="")
        //default is to POST at the same url, in this case "hello"
        //POST request rerouted to helloPost, since it is set up to handle POST requests
        //helloForm only handles GET requests
        //but both exist at /hello

        return html;
    }

    //handle requests submitted from form, POST method
    //need to have access to the request object
    //add HttpServletRequest request to helloPost as input parameter
    //to be able to get the data out of the request
    @RequestMapping(value = "hello", method=RequestMethod.POST)
    @ResponseBody
    public String helloPost(HttpServletRequest request) {
        // key "name" has to match key "name" in form
        String userName = request.getParameter("name");
        String language = request.getParameter( "language");

        return createMessage(userName, language);

        //return "Hello " + userName;
    }

    //configure url segment to be passed in as data instead of a route
    //ex: localhost:8080/hello/Melissa
    //Melissa will be passed in as data
    //{name} is param for handler method
    //specify PathVariable to match {name} to name
    //if url segment is null, returns to "hello" path and displays form

    @RequestMapping(value = "hello/{name}")
    @ResponseBody
    public String helloUrlSegment(@PathVariable String name) {
        return "Hello " + name;

    }

    @RequestMapping(value = "goodbye")
    // goodbye method available at /goodbye
    //@ResponseBody removed for redirect

    public String goodbye() {
        return "redirect:/";
    }

    public static String createMessage (String name, String language) {
        String greeting = "Hello";
        if (language.equals("French")) {
            greeting = "Bonjour";
        } else if (language.equals("Spanish")){
            greeting = "Hola";
        } else if (language.equals("English")){
            greeting = "Hello";
        }
        return greeting + ", " + name + "!";
    }
}
