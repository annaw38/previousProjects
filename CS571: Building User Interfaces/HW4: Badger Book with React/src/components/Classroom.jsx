import { Col, Button, Container, Pagination, Form, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import Student from "./Student";

const Classroom = () => {
    //students
    const [students, setStudents] = useState([]);
    const [searchedStuds, setSearchedStuds] = useState([]);
    const numStudents = searchedStuds.length;

    //student attributes
    const [searchName, setSearchName] = useState("");
    const [searchMajor, setSearchMajor] = useState("");
    const [searchInterest, setSearchInterest] = useState("");

    // pagination
    const [pageNum, setPageNum] = useState(1);
    const studsPerPage = 24
    const numPages = Math.ceil(numStudents/studsPerPage)
    // console.log(numPages)

    useEffect(() => {
        // fetching the data
        fetch("https://cs571api.cs.wisc.edu/rest/f25/hw4/students", {
            headers: {
                "X-CS571-ID": CS571.getBadgerId()
            }
        })
        .then(res => res.json())
        .then(data => {
            setStudents(data);
            setSearchedStuds(data);
            console.log(data);
        })
    }, []);

    // search for students
    function searchStudents() {
        // case 0: all fields are blank
        let result = students;

        // normalize the search terms to be lowercase and ignore leading and trailing whitespace
        const normName = searchName.toLowerCase().trim();
        const normInterest = searchInterest.toLowerCase().trim();
        const normMajor = searchMajor.toLowerCase().trim();
        // console.log("Name: " + searchName);
        // console.log("Major: " + searchMajor);
        // console.log("Interest: " + searchInterest);
        // case 1: all fields not blank
        if (searchName && searchMajor && searchInterest){
            // setSearchName(searchName);
            result = students.filter(s => {
                let fullName = s.name.first + ' '+ s.name.last;
                return s.interests.some(interest => {
                    return (interest.toLowerCase().includes(normInterest)
                    && fullName.toLowerCase().includes(normName) 
                    && s.major.toLowerCase().includes(normMajor));
                })
            })
            // console.log(result)
        }
        // case 2: name and major not blank
	    else if(searchName && searchMajor) {
            result = students.filter(s => {
                let fullName = s.name.first + ' ' + s.name.last;
                return (fullName.toLowerCase().includes(normName) 
                && s.major.toLowerCase().includes(normMajor));
            })
        }
        // case 3: name and interest not blank
        else if(searchName && searchInterest) {
            result = students.filter(s => {
                let fullName = s.name.first + ' ' + s.name.last;
                return s.interests.some(interest => {
                    return (interest.toLowerCase().includes(normInterest) 
                    && fullName.toLowerCase().includes(normName));
                })
            })
        }
        // case 4: major and interest not blank
        else if(searchMajor && searchInterest) {
            result = students.filter(s => {
                return s.interests.some(interest => {
                    return (interest.toLowerCase().includes(normInterest) 
                    && s.major.toLowerCase().includes(normMajor));
                })
            })
        }
        // case 5: name not blank
        else if (searchName){
            result = students.filter(s => {
                let fullName = s.name.first + ' ' + s.name.last;
                return fullName.toLowerCase().includes(normName);
            })
            // console.log(result);
            // console.log(searchName);
        }
        // case 6: major not blank
        else if(searchMajor) {
            result = students.filter(s => {
                return s.major.toLowerCase().includes(normMajor);
            })
        }
        // case 7: interest not blank
        else if (searchInterest) {
            // const normInterest = searchInterest.toLowerCase().trim();
            result = students.filter(s => {
                return s.interests.some(interest => {
                    return interest.toLowerCase().includes(normInterest);
                })
            })
        }
        // console.log(result);
        setSearchedStuds(result);
    }

    // reset search bar
    function resetSearch() {
        setSearchName("");
        setSearchMajor("");
        setSearchInterest("");
        setPageNum(1);
    }

    // search for students based on search bar inputs
    useEffect(() => {
        searchStudents();
        setPageNum(1);
        // console.log(pageNum)
        // console.log(numPages)
    }, [searchName, searchMajor, searchInterest]);

    return <div>
        <h1>Badger Book</h1>
        <p>Search for students below!</p>
        <hr />
        <Form>
            <Form.Label htmlFor="searchName">Name</Form.Label>
            <Form.Control id="searchName" value = {searchName} onChange={(e) => setSearchName(e.target.value)}/>
            <Form.Label htmlFor="searchMajor">Major</Form.Label>
            <Form.Control id="searchMajor" value = {searchMajor} onChange={(e) => setSearchMajor(e.target.value)}/>
            <Form.Label htmlFor="searchInterest">Interest</Form.Label>
            <Form.Control id="searchInterest" value = {searchInterest} onChange={(e) => setSearchInterest(e.target.value)}/>
            <br />
            <Button variant="neutral" onClick={resetSearch}>Reset Search</Button>
        </Form>
        <p>There are {numStudents} student(s) matching your search.</p>
        <Container fluid>
            <Row>
                { 
                    searchedStuds.length > 0 ? searchedStuds.slice(((pageNum) - 1) * 24, pageNum * 24).map(r=><Col xs={12} sm = {12} md = {6} lg = {4} xl={3} key ={r.id}>
                        <Student {...r} /></Col>) : <p>Loading students...</p>
                }
            </Row>
        </Container>
        <Pagination>
            <Pagination.Item disabled = {pageNum === 1 ? true : false} onClick = {()=> setPageNum(pageNum-1)}>Previous</Pagination.Item>
            {[...Array(numPages)].map((page, index) => (
                <Pagination.Item key = {index+1} active = {pageNum === index+1} onClick = {()=> setPageNum(index+1)}>
                    {index+1}
                </Pagination.Item>
            ))}
            <Pagination.Item disabled = {pageNum === numPages || (pageNum === 1 && numPages === 0) ? true : false} onClick = {()=> setPageNum(pageNum+1)}>Next</Pagination.Item>
        </Pagination>
    </div>
}

export default Classroom;