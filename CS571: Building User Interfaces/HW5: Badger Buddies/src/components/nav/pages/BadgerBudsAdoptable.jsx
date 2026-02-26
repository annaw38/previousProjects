import { useState, useContext, useEffect } from "react";
import { Container, Row, Col } from "react-bootstrap";
import BadgerBudsDataContext from "../../../contexts/BadgerBudsDataContext.js";
import Cat from "./BadgerBudSummary.jsx"

export default function BadgerBudsAdoptable(props) {

    const cats = useContext(BadgerBudsDataContext);
    // adoptable cats
    const [visibleCats, setVisibleCats] = useState([]);
    
    // filter for adoptable cats (not saved or adopted)
    useEffect(() => {
        const savedIds = JSON.parse(sessionStorage.getItem("savedCatIds") || "[]");
        const adoptedIds = JSON.parse(sessionStorage.getItem("adoptedCatIds") || "[]");
        if (cats && cats.length > 0) {
            setVisibleCats(cats.filter(cat => !savedIds.includes(cat.id) && !adoptedIds.includes(cat.id)));
        }
    }, [cats]);

    // saving a cat 
    const handleSave = (id) => {
        const savedIds = JSON.parse(sessionStorage.getItem("savedCatIds") || "[]");
        const updated = [...savedIds, id];
        sessionStorage.setItem("savedCatIds", JSON.stringify(updated));
        setVisibleCats(prevCats => prevCats.filter(cat => cat.id !== id));
    }

    return <div>
        <h1>Available Badger Buds</h1>
        <p>The following cats are looking for a loving home! Could you help?</p>
        <Container fluid>
            <Row>
                {
                    visibleCats.length > 0 ? visibleCats.map(c => <Col xs={12} sm = {12} md = {6} lg = {4} xl={3} xxl = {3} key = {c.id}>
                        <Cat {...c} onSave={handleSave} />
                        </Col>) : <p>No buds are available for adoption!</p>
                }
            </Row>
        </Container>
    </div>
}