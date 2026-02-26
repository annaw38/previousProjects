import { Image, ScrollView, Text, Linking, Animated, Pressable} from "react-native";
import { useEffect, useState, useRef } from 'react';
import CS571 from '@cs571/mobile-client';

function ArticleScreen(props) {
    // console.log(props)
    const [fullArticle, setFullArticle] = useState(null);
    const anim = useRef(new Animated.Value(0));

    // fetch full article 
    useEffect(() => {
        fetch(`https://cs571api.cs.wisc.edu/rest/f25/hw8/article?id=${props.route.params.fullArticleId}`, {
            headers: {
                "X-CS571-ID": CS571.getBadgerId()
            }
        })
        .then(res => res.json())
        .then(d => setFullArticle(d))
    }, []);
    // console.log(fullArticle)
    
    // animation fade in for 7 seconds
    useEffect(()=> {
        Animated.timing(anim.current, {
            toValue: 1, 
            duration: 7000,
            useNativeDriver: true
        }).start();
    }, [])

    // open link to full article's url
    function handlePress(){
        Linking.openURL(fullArticle.url);
    };

    return <ScrollView>
        <Image source={{uri: `https://raw.githubusercontent.com/CS571-F25/hw8-api-static-content/main/${props.route.params.img}`}} style={{width: 425, height: 250}}/>
        <Text style = {{ fontSize:20, fontWeight: 'bold', alignSelf: 'center', marginLeft: 5}}>{props.route.params.title}{'\n'}</Text>
        {fullArticle ? 
            <Animated.ScrollView style= {{opacity: anim.current}}>
                <Text style = {{fontSize:14, marginLeft: 5}}>By {fullArticle.author} on {fullArticle.posted}</Text>
                <Pressable onPress={handlePress}>
                    <Text style = {{fontSize: 14, color: 'blue', textDecorationLine: "underline", marginLeft: 5}}>Read full article here.</Text>
                </Pressable>
                <Text>{'\n'}</Text> 
                {fullArticle.body.map((paragraph, index) => (
                    <Text key={index} style={{fontSize:14, marginBottom:12, lineHeight:20, marginLeft: 5}}>
                    {paragraph}
                    </Text>
            ))}
        </Animated.ScrollView>
        : <Text style = {{marginLeft: 5}}>The content is loading!</Text>}
    </ScrollView>
}

export default ArticleScreen;