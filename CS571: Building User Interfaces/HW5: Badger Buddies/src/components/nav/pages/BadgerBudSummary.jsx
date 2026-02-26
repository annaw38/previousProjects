import { useState} from "react";
import { Button, Card, Carousel} from "react-bootstrap";

const Cat = (props) => {
    // button text
    const [buttonText, setButtonText] = useState("Show More");
    // button clicked or not
    const [showDetails, setDetails] = useState(false);

    // handle show more/less button click
    function handleClick() {
        setButtonText(buttonText === "Show More" ? "Show Less":"Show More")
        setDetails(showDetails === true ? false:true)
    }

    // handle moving cat to basket
    const moveToBasket = () => {
        alert(`${props.name} has been added to your basket!`);
        props.onSave(props.id);
        // console.log(`Moving ${props.id} to basket!`);
    }

    return <Card style={{margin: "0.25rem"}}> 
        {
            !showDetails && 
            <>
            <img src = {`https://raw.githubusercontent.com/CS571-F25/hw5-api-static-content/main/cats/${props.imgIds[0]}`} 
            alt = {`A picture of ${props.name}`} 
            style = {{width:300, height:300, aspectRatio: "1/1"}}/>
            <h2>{props.name}</h2>
            </>
        }    
        {
            showDetails &&
            <>
            <Carousel>
                {
                    props.imgIds.map(id => (
                        <Carousel.Item key = {id}>
                            <img src = {`https://raw.githubusercontent.com/CS571-F25/hw5-api-static-content/main/cats/${id}`} 
                            alt = {`A picture of ${props.name}`} 
                            style = {{width:300, height:300, aspectRatio: "1/1"}}
                            />
                        </Carousel.Item>
                    ))
                }
            </Carousel>
            <h2>{props.name}</h2>
            <p>{props.gender}</p>
            <p>{props.breed}</p>
            <p>{props.age}</p>
            <p>{props.description}</p>
            </>
        }
        <Button variant="primary" onClick={handleClick}>{buttonText}</Button>
        <Button variant="secondary" onClick={moveToBasket}>❤️ Save</Button>
    </Card>
}

export default Cat;