import React, { useEffect, useRef, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { BeatLoader } from 'react-spinners';

import TextAppMessageList from './TextAppMessageList';
import Constants from '../constants/Constants';

const CS571_WITAI_ACCESS_TOKEN = "3SSI6VDE7FAYJMEJDBBLFGA3IQ4KHHFU"; // Put your CLIENT access token here.

function TextApp() {

    // Set to true to block the user from sending another message
    const [isLoading, setIsLoading] = useState(false);

    const [messages, setMessages] = useState([]);
    const inputRef = useRef();

    // if resp1 = true return response type 1 else return response type 2
    const [isResp1, setResp1] = useState(true);
    /**
     * Called when the TextApp initially mounts.
     */
    async function handleWelcome() {
        addMessage(Constants.Roles.Assistant, "Welcome to BadgerChat! How can I help you?");
    }

    // called when get chatrooms intent is detected by witAI
    async function getChatrooms(witData) {
        // get the available chatrooms
        const res = await fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw10/chatrooms`, {
            method: "GET",
            headers: {
                "X-CS571-ID": CS571.getBadgerId(),
            }
        })
        const data = await res.json();
        // console.log(data)
        // console.log(data.join(', '))
        if (isResp1) {
            addMessage(Constants.Roles.Assistant, `You can visit... ${data.join(', ')}`);
            setResp1(false);
        } else{
            addMessage(Constants.Roles.Assistant, `These are the current available chatrooms: ${data.join(', ')}`);
            setResp1(true);
        }
    }

    // called when get_messages intent is detected by witAI
    async function getMessages(witData) {
        // check for specified room or number of messages
        const hasSpecifiedRoom = witData.entities["chatroom:chatroom"] ? true : false;
        const hasSpecifiedNumber = witData.entities["wit$number:number"] ? true : false;
        const chatroom = hasSpecifiedRoom ? witData.entities["chatroom:chatroom"][0].value : "any";

        // Assuming a user will always either (a) not type a number at all or (b) type in a number between 1 and 10.
        const numMsgs = hasSpecifiedNumber ? witData.entities["wit$number:number"][0].value : 1;

        const res = hasSpecifiedRoom ? 
            // if specified chatroom
            await fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw10/messages?chatroom=${chatroom}&num=${numMsgs}`, {
                method: "GET",
                headers: {
                    "X-CS571-ID": CS571.getBadgerId(),
                }
            }) :
            // otherwise get lastest numMsgs from any chatroom
            await fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw10/messages?num=${numMsgs}`, {
                method: "GET",
                headers: {
                    "X-CS571-ID": CS571.getBadgerId(),
                }
            })
        const data = await res.json();

        // console.log(data.messages);
        const messages = data.messages;

        // add messages 
        // fix change all of respType to isResp1 and setResp1
        // let respType = true; 
        for(let msg of messages) {
            // if (respType) {
            if(isResp1) {
                addMessage(Constants.Roles.Assistant, `${msg.poster} created a post titled '${msg.title}' in ${msg.chatroom} saying '${msg.content}'`);
                // respType = false;
                setResp1(false);
            }
            else {
                addMessage(Constants.Roles.Assistant, `In ${msg.chatroom}, ${msg.poster} created a post titled '${msg.title}' with content: '${msg.content}'`);
                // respType = true;
                setResp1(true);
            }
        }

    }
    /**
     * Called whenever the "Send" button is pressed.
     * @param {Event} e default form event; used to prevent from reloading the page.
     */
    async function handleSend(e) {
        e?.preventDefault();
        const input = inputRef.current.value?.trim();
        setIsLoading(true);
        if(input) {
            addMessage(Constants.Roles.User, input);
            inputRef.current.value = "";
            const resp = await fetch("https://api.wit.ai/message?q=" + encodeURIComponent(input), {
                headers: {
                    "Authorization": "Bearer " + CS571_WITAI_ACCESS_TOKEN
                }
            })
            const data = await resp.json();
            console.log(data);

            const matchedName = data.intents[0]?.name;
            // case 1 witai doesn't understand 
            if (!matchedName) {
                addMessage(Constants.Roles.Assistant, "I'm sorry, I don't understand!");
            }
            // case 2 get_help intent
            else if (matchedName === "get_help") {
                if (isResp1) {
                    addMessage(Constants.Roles.Assistant, "Try to get a list of chatrooms or the latest messages; just ask me!");
                    setResp1(false);
                } else{
                    addMessage(Constants.Roles.Assistant, "You can either get a list of chatrooms or the latest messages; just ask me!");
                    setResp1(true);
                }
            } 
            // case 3 get_chatrooms intent
            else if (matchedName === "get_chatrooms") {
                await getChatrooms(data);
            } 
            // case 4 get_messages intent
            else if (matchedName === "get_messages") {
                await getMessages(data);
            }
        }
        setIsLoading(false);
    }

    /**
     * Adds a message to the ongoing TextAppMessageList
     * 
     * @param {string} role The role of the message; either "user" or "assistant"
     * @param {*} content The content of the message
     */
    function addMessage(role, content) {
        setMessages(o => [...o, {
            role: role,
            content: content
        }]);
    }

    useEffect(() => {
        handleWelcome();
    }, []);

    return (
        <div className="app">
            <TextAppMessageList messages={messages}/>
            {isLoading ? <BeatLoader color="#36d7b7"/> : <></>}
            <div className="input-area">
                <Form className="inline-form" onSubmit={handleSend}>
                    <Form.Control
                        ref={inputRef}
                        style={{ marginRight: "0.5rem", display: "flex" }}
                        placeholder="Type a message..."
                        aria-label='Type and submit to send a message.'
                    />
                    <Button type='submit' disabled={isLoading}>Send</Button>
                </Form>
            </div>
        </div>
    );
}

export default TextApp;
