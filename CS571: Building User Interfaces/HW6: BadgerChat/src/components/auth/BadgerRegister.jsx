import React from 'react';
import { useState,  useContext} from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router";
import BadgerLoginStatusContext from "../contexts/BadgerLoginStatusContext.js";

export default function BadgerRegister() {

    // controlled input components 
    const [username, setUsername] = useState("");
    const [pin, setPin]= useState("");
    const [repeatPin, setRepeatPin] = useState("");
    const [loginStatus, setLoginStatus] = useContext(BadgerLoginStatusContext);
    let navigate = useNavigate(); //navigating to homepage after registering

    function handleRegister(e) {
        e?.preventDefault();  // prevents default form submit action
        const pinLength = /^\d{7}$/;
        // check that the user entered username and pin
        if (!username || !pin){
            alert("You must provide both a username and pin!");
        } 
        // check that the user entered a pin and repeat pin that's 7 digits
        else if (pinLength.test(pin) == false || pinLength.test(repeatPin) == false) {
            alert("Your pin must be a 7-digit number!");
        }
        // check that the pin and repeat pins match
        else if (pin != repeatPin) {
            alert("Your pins do not match!");
        }else{
            // console.log(username)
            // console.log(pin)
            // console.log(repeatPin)
            fetch("https://cs571api.cs.wisc.edu/rest/f25/hw6/register", {
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
                // check if username is already taken
                if (res.status === 409) {
                    alert("Sorry, this username is taken!");
                }else if (res.status === 200) {
                    alert("You have been successfully registered!");
                    const updated = [username];
                    setLoginStatus(updated);
                    sessionStorage.setItem("savedUsers", JSON.stringify(updated));
                    // console.log(loginStatus);
                    navigate("/");
                }
            })
        }
    }

    return <>
        <h1>Register</h1>
        <Form onSubmit={handleRegister}>
            <Form.Label htmlFor="usernameInput">Username</Form.Label>
            <Form.Control id="usernameInput" value = {username} onChange={(e) => setUsername(e.target.value)}></Form.Control>
            <Form.Label htmlFor="pinInput">Pin</Form.Label>
            <Form.Control id="pinInput" type="password" value = {pin} onChange={(e) => setPin(e.target.value)}></Form.Control>
            <Form.Label htmlFor="repeatPinInput">Repeat Pin</Form.Label>
            <Form.Control id="repeatPinInput" type="password" value = {repeatPin} onChange={(e) => setRepeatPin(e.target.value)}></Form.Control>
            <br/>
            <Button type="submit" onClick={handleRegister}>Register</Button>
        </Form>
    </>
}