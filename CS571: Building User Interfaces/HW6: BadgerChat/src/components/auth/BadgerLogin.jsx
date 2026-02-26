import React from 'react';
import {  useContext, useRef } from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router";
import BadgerLoginStatusContext from "../contexts/BadgerLoginStatusContext.js";

export default function BadgerLogin() {

    // 	potential codes: 200, 400, 401
    // uncontrolled input components
    const usernameRef = useRef();
    const pinRef = useRef();
    const [loginStatus, setLoginStatus] = useContext(BadgerLoginStatusContext);
    let navigate = useNavigate();

    function handleLogin(e) {
        e?.preventDefault();
        let username = usernameRef.current.value;
        let pin = pinRef.current.value;
        const pinLength = /^\d{7}$/;
        // check that user entered username and pin
        if (!username || !pin) {
            alert("You must provide both a username and pin!");
        } 
        // check that user's pin is 7 digits
        else if (pinLength.test(pin) == false) {
            alert("Your pin is a 7-digit number!")
        } else {
            fetch("https://cs571api.cs.wisc.edu/rest/f25/hw6/login", {
                method: "POST",
                credentials: "include",
                headers: {
                    "X-CS571-ID": CS571.getBadgerId(),
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    pin: pin
                })
            })
            .then(res => {
                if (res.status === 200) {
                    alert("You have been successfully logged in!");
                    const updated = [username];
                    setLoginStatus(updated);
                    sessionStorage.setItem("savedUsers", JSON.stringify(updated));
                    // console.log(loginStatus);
                    // console.log(sessionStorage.getItem("savedUsers"));
                    navigate("/");
                }
                // check if the username and password were incorrect 
                else if (res.status === 401) {
                    alert("Incorrect username or pin!");
                } 
            })
        }
    }

    return <>
        <h1>Login</h1>
        <Form onSubmit={handleLogin}>
            <Form.Label htmlFor="usernameInput">Username</Form.Label>
            <Form.Control id="usernameInput" ref={usernameRef}></Form.Control>
            <Form.Label htmlFor="pinInput">Pin</Form.Label>
            <Form.Control id="pinInput" type="password" ref={pinRef}></Form.Control>
            <br/>
            <Button type="submit" onClick={handleLogin}>Login</Button>
        </Form>
    </>
}