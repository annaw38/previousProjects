import { Button, Text, View, Alert, FlatList } from "react-native";
import { useEffect, useState} from "react";
import BadgerSaleItem from "./BadgerSaleItem";

import CS571 from '@cs571/mobile-client'

export default function BadgerMart(props) {
    // current item 
    const [item, setItem] = useState([{
        name: "Item name",
        price: 0.00, 
        description: "Item description", 
        upperLimit: 0, 
        imgSrc: undefined
    }]);
    // pagination
    const [pageNum, setPageNum] = useState(1);
    const numPages = item.length;
    // are the previous, next, and place order buttons disabled?
    const [isPrevDisabled, setIsPrevDisabled] = useState(true);
    const [isNextDisabled, setIsNextDisabled] = useState(false);
    const [isOrderDisabled, setIsOrderDisabled] = useState(true);

    // total order cost
    const [totalCost, setTotalCost] = useState(0);
    // current item 
    const [selectedItem, setSelectedItem] = useState(null);
    
    // current number of items and total number of items
    const [numItems, setNumItems] = useState(0);
    const [totalItems, setTotalItems] = useState(0);
    
    useEffect(() => {
        fetch("https://cs571api.cs.wisc.edu/rest/f25/hw7/items", {
            headers: {
                "X-CS571-ID": CS571.getBadgerId()
            }
        })
        .then(res => res.json())
        .then(d => setItem(d))
    }, []);

    // handle the previous button 
    function handlePrev() {
        setPageNum(pageNum - 1);
        setSelectedItem(item.slice((pageNum - 1) * 1, pageNum * 1));
    }

    // handle the next button
    function handleNext() {
        setPageNum(pageNum + 1);
        setSelectedItem(item.slice((pageNum - 1) * 1, pageNum * 1));
    }

    // set previous and next buttons to disabled based off if first or last item
    useEffect(() => {
        setIsPrevDisabled(pageNum <= 1);
        setIsNextDisabled(pageNum >= numPages);
    }, [pageNum, numPages]);

    // handle plus item button 
    function handleAdd(cost) {
        setNumItems(numItems + 1);
        setTotalItems(totalItems + 1);
        setTotalCost(totalCost + parseFloat(cost));
    }

    // handle subtract item button
    function handleSub(cost) {
        setNumItems(numItems - 1);
        setTotalItems(totalItems - 1);
        setTotalCost(totalCost - parseFloat(cost));
    }

    // handle place order button
    function handleOrder(){
        Alert.alert("Order Confirmed!", `Your order contains ${totalItems} items and would have cost $${totalCost.toFixed(2)}!`);
        setPageNum(1);
        setNumItems(0);
        setTotalItems(0);
        setTotalCost(0);
    }

    // disable place order button if nothing in cart
    useEffect(()=> {
        if (totalItems <= 0) {
            setIsOrderDisabled(true);
        } else{
            setIsOrderDisabled(false);
        }
    }, [totalItems]);

    // if item changes change the number of items to 0
    useEffect(() => {
        setNumItems(0);
    }, [selectedItem]);

    return <View style= {{marginTop: 40, textAlign: 'center'}}>
        <Text style={{fontSize: 28, textAlign: 'center'}}>Welcome to Badger Mart!</Text> 
        <View style = {{flexDirection: 'row', alignItems: 'center', justifyContent: 'center'}}>
            <Button title="Previous" onPress={handlePrev} disabled={isPrevDisabled}/>
            <Button title="Next" onPress={handleNext} disabled={isNextDisabled}/>
        </View>
        {/* check if item is available */}
        {item ? (
            <FlatList
            data={item.slice((pageNum - 1) * 1, pageNum * 1)}
            renderItem={({item}) => {
                // console.log(item);
                return <BadgerSaleItem {...item} onAdd = {handleAdd} onSub = {handleSub} itemsNumber = {numItems}/>;
            }}
            keyExtractor={(item) => item.name}
            />
        ) : (
        <Text>Loading Items...</Text>
        )}
        <Text style={{marginBottom:20, fontSize: 16, textAlign: 'center'}}>You have {totalItems} item(s) in costing ${totalCost.toFixed(2)} in your cart!</Text>
        <Button title="Place Order" onPress={handleOrder} disabled={isOrderDisabled}/> 
    </View>
}