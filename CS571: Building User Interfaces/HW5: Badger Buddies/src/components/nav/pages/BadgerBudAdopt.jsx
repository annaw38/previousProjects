import { Button, Card } from "react-bootstrap";

const Cat = (props) => {
    // handle the unsave button
    function handleUnsave() {
        alert(`${props.name} has been removed from your basket!`);
        props.onDeselect(props.id);
    }

    // handle the adoption button
    function handleAdoption() {
        alert(`${props.name} has been adopted!`);
        props.onAdopt(props.id);
    }

    return <Card style={{margin: "0.25rem"}}> 
        <img src = {`https://raw.githubusercontent.com/CS571-F25/hw5-api-static-content/main/cats/${props.imgIds[0]}`} 
        alt = {`A picture of ${props.name}`} 
        style = {{width:300, height:300, aspectRatio: "1/1"}}/>
        <h2>{props.name}</h2>
        <Button variant="secondary" onClick={handleUnsave}>Unselect</Button>
        <Button variant="success" onClick={handleAdoption}>😻 Adopt</Button>
    </Card>
}

export default Cat;
