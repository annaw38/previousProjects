import React, { useState } from "react";
import { Container, Nav, Navbar, NavDropdown, NavItem, NavLink } from "react-bootstrap";
import { Link, Outlet } from "react-router";

import crest from '../../assets/uw-crest.svg'
import BadgerLoginStatusContext from "../contexts/BadgerLoginStatusContext";

function BadgerLayout(props) {
    // const savedUsers = sessionStorage.getItem("savedUsers")
    const [loginStatus, setLoginStatus] = useState(sessionStorage.getItem("savedUsers") || JSON.stringify([]));
    // console.log(loginStatus)
    // console.log(loginStatus.length)

    console.log(props)
    if (loginStatus.length > 0 && loginStatus !== "[]") {
        return <>
            <div>
                <Navbar bg="dark" variant="dark">
                    <Container>
                        <Navbar.Brand as={Link} to="/">
                            <img
                                alt="BadgerChat Logo"
                                src={crest}
                                width="30"
                                height="30"
                                className="d-inline-block align-top"
                            />{' '}
                            BadgerChat
                        </Navbar.Brand>
                        <Nav className="me-auto">
                            <Nav.Link as={Link} to="/">Home</Nav.Link>
                            <Nav.Link as={Link} to="logout">Logout</Nav.Link>
                            <NavDropdown title="Chatrooms">
                                {
                                    props.chatrooms.length > 0 ? 
                                    props.chatrooms.map(room => <NavDropdown.Item as={Link} to={`chatrooms/${room}`} key = {room}>{room}</NavDropdown.Item>) 
                                    : <p> Waiting for chatrooms.</p>
                                }
                            </NavDropdown>
                        </Nav>
                    </Container>
                </Navbar>
                <div style={{ margin: "1rem" }}>
                    <BadgerLoginStatusContext.Provider value={[loginStatus, setLoginStatus]}>
                        <Outlet />
                    </BadgerLoginStatusContext.Provider>
                </div>
            </div>
        </>
    }
    else{
        return (
            <div>
                <Navbar bg="dark" variant="dark">
                    <Container>
                        <Navbar.Brand as={Link} to="/">
                            <img
                                alt="BadgerChat Logo"
                                src={crest}
                                width="30"
                                height="30"
                                className="d-inline-block align-top"
                            />{' '}
                            BadgerChat
                        </Navbar.Brand>
                        <Nav className="me-auto">
                            <Nav.Link as={Link} to="/">Home</Nav.Link>
                            <Nav.Link as={Link} to="login">Login</Nav.Link>
                            <Nav.Link as={Link} to="register">Register</Nav.Link>
                            <NavDropdown title="Chatrooms">
                                {
                                    props.chatrooms.length > 0 ? 
                                    props.chatrooms.map(room => <NavDropdown.Item as={Link} to={`chatrooms/${room}`} key = {room}>{room}</NavDropdown.Item>) 
                                    : <p> Waiting for chatrooms.</p>
                                }
                            </NavDropdown>
                        </Nav>
                    </Container>
                </Navbar>
                <div style={{ margin: "1rem" }}>
                    <BadgerLoginStatusContext.Provider value={[loginStatus, setLoginStatus]}>
                        <Outlet />
                    </BadgerLoginStatusContext.Provider>
                </div>
            </div>
        );
    }
}

export default BadgerLayout;