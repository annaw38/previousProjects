import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import Ionicons from '@expo/vector-icons/Ionicons';
import Feather from '@expo/vector-icons/Feather';
import BadgerNewsStack from "../screens/BadgerNewsStack";
import BadgerPreferencesScreen from "../screens/BadgerPreferencesScreen";


const newsTab = createBottomTabNavigator();

function BadgerTabs(props) {
    return (
        <newsTab.Navigator screenOptions={{headerShown: false}}>
            <newsTab.Screen 
                name="News" 
                options = {{title: "News", tabBarActiveTintColor: "red", tabBarInactiveTintColor: "black", tabBarIcon: () => (<Ionicons name="newspaper-sharp" size={24} />)}}>
                    {() => <BadgerNewsStack articles={props.articles} />}
            </newsTab.Screen>
            <newsTab.Screen 
                name="Preferences" 
                options = {{headerShown: true, title: "Preferences", tabBarActiveTintColor: "red", tabBarInactiveTintColor: "black", tabBarIcon: () => (<Feather name="settings" size={24}/>)}}>
                    {() => <BadgerPreferencesScreen articles={props.articles} />}
            </newsTab.Screen>
        </newsTab.Navigator>
    );
}

export default BadgerTabs;