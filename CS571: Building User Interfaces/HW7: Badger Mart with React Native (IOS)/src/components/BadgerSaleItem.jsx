import { useEffect, useState } from "react";
import { Text, View, Image, Button } from "react-native";

export default function BadgerSaleItem(props) {
    // console.log(props)
    // are the minus and plus buttons disabled? 
    const [isMinusDisabled, setIsMinusDisabled] = useState(false);
    const [isPlusDisabled, setIsPlusDisabled] = useState(true);

    // handle adding an item to cart
    function handlePlus() {
        props.onAdd(props.price.toFixed(2));
    }
    // handle removing an item from the cart
    function handleMinus() {
        props.onSub(props.price.toFixed(2));
    }
    // disable buttons based on if none or all of item is in cart
    useEffect(() => {
        setIsMinusDisabled(props.itemsNumber <= 0);
        setIsPlusDisabled(props.itemsNumber >= props.upperLimit);
    }, [props.itemsNumber]);

    return <View>
        <Image style={{width:370, height:370, alignSelf: 'center'}} source = {{uri: props.imgSrc}}/>
        <Text style = {{fontSize: 45, textAlign: 'center', padding: 10}}>{props.name}</Text>
        <Text style = {{fontSize: 30,  textAlign: 'center', padding: 10}}>${props.price.toFixed(2)} each</Text>
        <Text style = {{fontSize: 25,  textAlign: 'center', padding: 10}}>You can order up to {props.upperLimit} units!</Text>
         <View style = {{marginBottom: 175, flexDirection: 'row', alignItems: 'center', justifyContent: 'center'}}>
            <Button title="-" onPress={handleMinus} disabled={isMinusDisabled}/>
            <Text style={{ fontSize: 25}}>{props.itemsNumber}</Text>
            <Button title="+" onPress={handlePlus} disabled={isPlusDisabled}/>
        </View> 
    </View>
}