import React from "react"
import { Button, Card } from "react-bootstrap";

function BadgerMessage(props) {
    const savedUsers = JSON.parse(sessionStorage.getItem("savedUsers"));
    // console.log(savedUsers)
    // console.log(props)
    const dt = new Date(props.created);

    function handleDelete(){
        props.onDelete(props.id)
        // console.log(props.id)
    }

    return <Card style={{margin: "0.5rem", padding: "0.5rem"}}>
        <h2>{props.title}</h2>
        <sub>Posted on {dt.toLocaleDateString()} at {dt.toLocaleTimeString()}</sub>
        <br/>
        <i>{props.poster}</i>
        <p>{props.content}</p>
        {
            savedUsers !== null && savedUsers.some(user => user === props.poster) && (
                <Button variant="danger" onClick={handleDelete}>Delete Post</Button>
            )
        }
    </Card>
}

export default BadgerMessage;