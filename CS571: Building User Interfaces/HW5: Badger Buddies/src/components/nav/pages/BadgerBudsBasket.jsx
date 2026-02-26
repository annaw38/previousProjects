import { useContext, useState, useEffect} from "react";
import { Container, Row, Col } from "react-bootstrap";
import BadgerBudsDataContext from "../../../contexts/BadgerBudsDataContext.js";
import Cat from "./BadgerBudAdopt.jsx"

export default function BadgerBudsBasket(props) {

    const cats = useContext(BadgerBudsDataContext);
    // saved cats in basket 
    const [visibleCats, setVisibleCats] = useState([]);

    // identify the cats saved but not adopted yet
    useEffect(() => {
        const savedIds = JSON.parse(sessionStorage.getItem("savedCatIds") || "[]");
        const adoptedIds = JSON.parse(sessionStorage.getItem("adoptedCatIds") || "[]");
        if (cats && cats.length > 0) {
            setVisibleCats(cats.filter(cat => savedIds.includes(cat.id) && !adoptedIds.includes(cat.id)));
        }
    }, [cats]);
    
    // handle the unsave case
    const handleDeselect = (id) => {
        const savedIds = JSON.parse(sessionStorage.getItem("savedCatIds") || "[]");
        sessionStorage.setItem("savedCatIds", JSON.stringify(savedIds.filter(catId => catId !== id)));
        setVisibleCats(prevCats => prevCats.filter(cat => cat.id !== id));
    }

    // handle the adopt case 
    const handleAdopt = (id) => {
        // add the adopted cat's id to adopted ids
        const adoptedIds = JSON.parse(sessionStorage.getItem("adoptedCatIds") || "[]");
        const updated = [...adoptedIds, id];
        sessionStorage.setItem("adoptedCatIds", JSON.stringify(updated));

        // remove the adopted cat's id from saved ids
        const savedIds = JSON.parse(sessionStorage.getItem("savedCatIds") || "[]");
        const updatedSaved = savedIds.filter(catId => catId !== id);
        sessionStorage.setItem("savedCatIds", JSON.stringify(updatedSaved));
        
        // update the cats in basket
        setVisibleCats(prevCats => prevCats.filter(cat => cat.id !== id));
    }

    return <div>
        <h1>Badger Buds Basket</h1>
        <p>These cute cats could be all yours!</p>
        <Container fluid>
            <Row>
                {
                    visibleCats.length > 0 ? visibleCats.map(c => <Col xs={12} sm = {12} md = {6} lg = {4} xl={3} xxl = {3} key = {c.id}>
                        <Cat {...c} onDeselect = {handleDeselect} onAdopt = {handleAdopt}/>
                        </Col>) : <p>You have no buds in your basket!</p>
                }
            </Row>

        </Container>
    </div>
}