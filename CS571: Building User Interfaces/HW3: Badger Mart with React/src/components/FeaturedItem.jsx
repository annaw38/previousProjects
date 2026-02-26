import { useState } from "react";
import { Button, Card, Table } from "react-bootstrap";

export default function FeaturedItem(props) {
    // console.log(props)
    const [buttonText, setButtonText] = useState("Show Nutrition Facts");
    const [showTable, setTable] = useState(false);
    function handleClick() {
        setButtonText(buttonText === "Show Nutrition Facts"?"Hide Nutrition Facts":"Show Nutrition Facts")
        setTable(showTable === true ? false: true)
    }

    return <Card style={{border: "dotted"}}>
        <img src = {props.img} alt = {props.description} style = {{width:300, height:300}}/>
        <h3>{props.name}</h3>
        <h4>${props.price} per unit</h4>
        <p>{props.description}</p>
        {showTable && 
            <><h5>Nutrition Facts</h5><Table>
                <thead>
                    <tr>
                        <th>Calories</th>
                        <th>Fat</th>
                        <th>Carbohydrates</th>
                        <th>Protein</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{props.nutrition.calories}</td>
                        <td>{props.nutrition.fat ? props.nutrition.fat : "0g"}</td>
                        <td>{props.nutrition.carbohydrates ? props.nutrition.carbohydrates : "0g"}</td>
                        <td>{props.nutrition.protein ? props.nutrition.protein : "0g"}</td>
                    </tr>
                </tbody>
            </Table></>
        }
        <Button onClick={handleClick} className = "button">{buttonText}</Button>
    </Card>
}
