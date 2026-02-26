import { useState } from "react";
import TextApp from "./TextApp";

import { Container, Dropdown, Nav, NavItem, NavLink } from "react-bootstrap";

import useStorage from "../useStorage";

export default function TextAppManager() {

    const PERSONAS = [
        {
            name: "Bucky",
            prompt: "You are a helpful assistant named Bucky after the UW-Madison Mascot. Your goal is to help the user with whatever queries they have.",
            initialMessage: "Hello, my name is Bucky. How can I help you?"
        },
        {
            name: "Pirate Pete",
            prompt: "You are a helpful pirate assisting your mateys with their questions. Respond like a pirate would. Your goal is to help the user with whatever queries they have.",
            initialMessage: "Hello, my name is Pete the Pirate. How can I help you?"
        }, 
        {
            name: "Cedric the Great",
            prompt: "You are a wizard assisting muggles and other non magical people with their questions. Respond like a wizard or magical being would. Your goal is to help the user with whatever queries they have.",
            initialMessage: "Hello, I'm a wizard named Cedric the Great. How can I help you?"
        }
    ];

    const [personaName, setPersonaName] = useStorage("persona", PERSONAS[0].name);
    const persona = PERSONAS.find(p => p.name === personaName);
    const [isResetNeeded, setIsResetNeeded] = useState(0);

    function handleNewChat() {
        // increase isResetNeeded to force remount for new chat
        setIsResetNeeded(prev=> prev + 1);
        // console.log(isRequestNeeded)
    }

    function handleSwitchPersona(selectedPersona) {
        if (selectedPersona === personaName) return;
        setPersonaName(selectedPersona);
        // console.log(persona.name)
        // increase isResetNeeded to force remount for new persona
        setIsResetNeeded(prev=> prev + 1);
    }

    return <Container style={{ marginTop: "0.25rem" }}>
        <Nav justify variant="tabs">
            <Nav.Item>
                <Nav.Link onClick={handleNewChat}>New Chat</Nav.Link>
            </Nav.Item>
            <Dropdown as={NavItem} onSelect={handleSwitchPersona}>
                <Dropdown.Toggle as={NavLink}>Personas</Dropdown.Toggle>
                <Dropdown.Menu >
                    {
                        PERSONAS.map(p => <Dropdown.Item key={p.name} eventKey={p.name} active={personaName === p.name}>{p.name}</Dropdown.Item>)
                    }
                </Dropdown.Menu>
            </Dropdown>
        </Nav>
        <TextApp persona={persona} isResetNeeded = {isResetNeeded}/>
    </Container>
}