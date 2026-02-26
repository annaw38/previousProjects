//describing a student
const Student = (props) => {
    return <div>
        <h2>{props.name.first} {props.name.last}</h2>
        <h4>{props.major}</h4>
        <p>{props.name.first} is taking {props.numCredits} credits and {props.fromWisconsin === true ? "is" : "is not"} from Wisconsin.</p>
        <p>They have {props.interests.length} interests: </p>
        <ul>
           {props.interests.map((interest, index)=>(
                <li key = {index}>{interest}</li>
           ))}
        </ul>
    </div>
}

export default Student;