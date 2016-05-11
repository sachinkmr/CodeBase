<%-- 
    Document   : demo
    Created on : 6 Feb, 2015, 12:15:39 AM
    Author     : Sachin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="getMessages();">
        <h1>SHOUT-OUT!</h1>
        <form>
            <table>                
                <tr>
                    <td><input type="button" onclick="postMessage();" value="SHOUT" /></td>
                </tr>
            </table>
        </form>
        <h2> Current Shouts </h2>
        <div id="content">
           
        </div>
        <script>
            function postMessage() {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("POST", "ShoutServlet?t="+new Date(), false);
                xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                var nameText = escape(document.getElementById("name").value);
                var messageText = escape(document.getElementById("message").value);
                document.getElementById("message").value = "";
                xmlhttp.send("name="+nameText+"&message="+messageText);
            }
            var messagesWaiting = false;
            function getMessages(){
                if(!messagesWaiting){
                    messagesWaiting = true;
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function(){
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                            messagesWaiting = false;
                            var contentElement = document.getElementById("content");
                            contentElement.innerHTML = xmlhttp.responseText + contentElement.innerHTML;
                        }
                    }
                    xmlhttp.open("GET", "ShoutServlet?t="+new Date(), true);
                    xmlhttp.send();
                }
            }
            setInterval(getMessages, 1000);
        </script>
    </body>
</html>