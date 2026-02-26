import React, { useEffect, useState, useRef } from "react"
import { Container, Row, Col, Pagination, Button, Form} from "react-bootstrap";
import BadgerMessage from "./BadgerMessage";

export default function BadgerChatroom(props) {

    const [messages, setMessages] = useState([]);
    const [pageNum, setPageNum] = useState(1);

    const [loginStatus, setLoginStatus] = useState(sessionStorage.getItem("savedUsers") || JSON.stringify([]));
    // uncontrolled input components
    const titleRef = useRef();
    const valueRef = useRef();
    // console.log(loginStatus)

    const loadMessages = () => {
        fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw6/messages?chatroom=${props.name}&page=${pageNum}`, {
            headers: {
                "X-CS571-ID": CS571.getBadgerId()
            }
        }).then(res => res.json()).then(json => {
            setMessages(json.messages)
            // console.log("messages", messages)
            // console.log("page number", pageNum)
        })
    };

    // Why can't we just say []?
    // The BadgerChatroom doesn't unload/reload when switching
    // chatrooms, only its props change! Try it yourself.
    useEffect(loadMessages, [props]);

    // update page number to 1 when switching chatrooms
    useEffect(()=> {
        setPageNum(1)
    }, [props]);

    // update the messages when the page number updates
    useEffect(() => {
        loadMessages();
    }, [pageNum]);

    function handlePost(e) {
        e?.preventDefault();

        let title = titleRef.current.value;
        let value = valueRef.current.value;
        // check that a title and content was provided
        if (!title || !value) {
            // console.log(title)
            alert("You must provide both a title and content!");
        }  else {
            fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw6/messages?chatroom=${props.name}`, {
                method: "POST",
                credentials: "include",
                headers: {
                    "X-CS571-ID": CS571.getBadgerId(),
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    title: title,
                    content: value
                })
            })
            .then(res => {
                if (res.status === 200) {
                    // notify user their post has been posted and load messages
                    alert("Successfully posted!");
                    loadMessages();
                } 
            })
        }
    }

    const handleDeletePost = (msgId) => {
        fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw6/messages?id=${msgId}`, {
            method: "DELETE",
            credentials: "include",
            headers: {
                "X-CS571-ID": CS571.getBadgerId(),
                "Content-Type": "application/json"
            }
        })
        .then(res => {
            if (res.status === 200) {
                // notify user their post was deleted and load messages
                alert("Successfully deleted the post!");
                loadMessages();
            } 
        })
    }
   
    // console.log(messages)
    // console.log(pageNum)
    if (loginStatus.length > 0 && loginStatus !== "[]") {
        return <>
            <h1>{props.name} Chatroom</h1>
            <hr/>
            {
                messages.length > 0 ?
                    <>
                        <Container>
                            <Row>
                                <Col xs={6} sm = {6} md = {4} lg = {4} xl={4} xxl = {3}>
                                    <Form onSubmit={handlePost}>
                                        <Form.Label htmlFor="titleInput">Post Title</Form.Label>
                                        <Form.Control id="titleInput" ref={titleRef}></Form.Control>
                                        <Form.Label htmlFor="valueInput">Post Content</Form.Label>
                                        <Form.Control id="valueInput" ref={valueRef}></Form.Control>
                                        <br/>
                                        <Button type="submit" onClick={handlePost}>Create Post</Button>
                                    </Form>
                                </Col>
                                <Col xs={6} sm = {6} md = {8} lg = {8} xl={8} xxl = {9}>
                                    <Row>
                                        {
                                            messages.map(msg => <Col xs={12} sm = {12} md = {6} lg = {6} xl = {4} xxl = {4} key = {msg.created}>
                                            <BadgerMessage {...msg} onDelete = {handleDeletePost}/></Col>)
                                        }
                                    </Row>
                                </Col>
                            </Row>
                        </Container>
                        <Pagination>
                            <Pagination.Item key = {1} active = {pageNum === 1} onClick = {()=> setPageNum(1)}>
                                {1}
                            </Pagination.Item>
                            <Pagination.Item key = {2} active = {pageNum === 2} onClick = {()=> setPageNum(2)}>
                                {2}
                            </Pagination.Item>
                            <Pagination.Item key = {3} active = {pageNum === 3} onClick = {()=> setPageNum(3)}>
                                {3}
                            </Pagination.Item>
                            <Pagination.Item key = {4} active = {pageNum === 4} onClick = {()=> setPageNum(4)}>
                                {4}
                            </Pagination.Item>
                        </Pagination> 
                    </>
                    :
                    <>
                        <p>There are no messages on this page yet!</p>
                    </>
            }
        </>
    }
    // not logged in
    else{
        return <>
            <h1>{props.name} Chatroom</h1>
            <hr/>
            {
                messages.length > 0 ?
                    <>
                        <Container>
                            <Row>
                                <Col xs={6} sm = {6} md = {4} lg = {4} xl={4} xxl = {3}>
                                    <p>You must be logged in to post!</p>
                                </Col>
                                <Col xs={6} sm = {6} md = {8} lg = {8} xl={8} xxl = {9}>
                                    <Row>
                                        {
                                            messages.map(msg => <Col xs={12} sm = {12} md = {6} lg = {6} xl = {4} xxl = {4} key = {msg.created}>
                                            <BadgerMessage {...msg}/></Col>) 
                                        }
                                    </Row>
                                </Col>
                            </Row>
                        </Container>
                        <Pagination>
                            <Pagination.Item key = {1} active = {pageNum === 1} onClick = {()=> setPageNum(1)}>
                                {1}
                            </Pagination.Item>
                            <Pagination.Item key = {2} active = {pageNum === 2} onClick = {()=> setPageNum(2)}>
                                {2}
                            </Pagination.Item>
                            <Pagination.Item key = {3} active = {pageNum === 3} onClick = {()=> setPageNum(3)}>
                                {3}
                            </Pagination.Item>
                            <Pagination.Item key = {4} active = {pageNum === 4} onClick = {()=> setPageNum(4)}>
                                {4}
                            </Pagination.Item>
                        </Pagination> 
                    </>
                    :
                    <>
                        <p>There are no messages on this page yet!</p>
                    </>
            }
        </>
    }
}
