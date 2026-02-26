let students = null;

// fetch student datas
fetch("https://cs571api.cs.wisc.edu/rest/f25/hw2/students", {
        headers: {
            "X-CS571-ID": CS571.getBadgerId()
        }
	})
	.then(r => {
		console.log(r);
		return r.json();
	})
	.then(data => {
		console.log(data);
		students = data;
		buildStudents(data);
	})

// Add the students
function buildStudents(studs) {
	document.getElementById("num-results").innerText = studs.length;
	const container = document.querySelector(".container-fluid");
	for (let student of studs) {
		// new student node 
		let studentNode = document.getElementById("students");
		// add a new student
		let newStudent = document.createElement("div");
		// responsive design
		newStudent.classList.add("col-12", "col-sm-12", "col-md-6", "col-lg-4", "col-xl-3", "col-xxl-3");
		//student name
		let studentName = document.createElement("h3");
		studentName.innerText = student.name.first + " " + student.name.last;
		//student major
		let studentMajor = document.createElement("h4");
		studentMajor.innerText = student.major;
		//number of credits and from wisconsin?
		let description = document.createElement("p");
		if (student.fromWisconsin === true) {
			description.innerText = student.name.first + " is taking " + student.numCredits 
			+ " credits and is from Wisconsin.";
		} else {
			description.innerText = student.name.first + " is taking " + student.numCredits 
			+ " credits and is NOT from Wisconsin.";
		}

		//student interests
		let interestDesc = document.createElement("p");
		interestDesc.innerText = "They have " + student.interests.length + " interests: ";
		
		let interestNode = document.createElement("ul");
		for(let interest of student.interests) {
			let newInterestNode = document.createElement("li");
			newInterestNode.innerText = interest;
			//on click search for people with similar interests
			newInterestNode.addEventListener("click", (e) => {
				const selectedText = e.target.innerText;
				document.getElementById("search-name").value = '';
				document.getElementById("search-major").value = '';
				document.getElementById("search-interest").value = selectedText;
				handleSearch();
			});
			interestNode.appendChild(newInterestNode);
		}
		
		newStudent.appendChild(studentName);
		newStudent.appendChild(studentMajor);
		newStudent.appendChild(description);
		newStudent.appendChild(interestDesc);
		newStudent.appendChild(interestNode);
		studentNode.appendChild(newStudent);
		container.appendChild(studentNode);
	}
}

// Search for students by name, major, or interest
function handleSearch(e) {
	e?.preventDefault(); // You can ignore this; prevents the default form submission!

	const name = document.getElementById("search-name");
	const nameValue = name.value.toLowerCase().trim();
	// console.log(nameValue);

	const major = document.getElementById("search-major");
	const majorValue = major.value.toLowerCase().trim();
	// console.log(majorValue);

	const interest = document.getElementById("search-interest");
	const interestValue = interest.value.toLowerCase().trim();
	// console.log(interestValue);

	/*
	Requirements: 
	- search terms are case-insensitive, e.g. searching "cat" should yield results with "cAT"
	- search terms are substrings, e.g. "olo" should yield results with "color"
	- search terms are AND expressions, e.g. searching for a name of "Cole", a major of "Computer Science", and an interest of "coffee" should only yield Coles studying computer science who are interested in coffee
	searching "john", "smith", "john smith", or "ohn smi", should all yield the person named "John Smith"
	- you can achieve this by concatenating each person's first and last name with a space; if the search name is a substring of this concatenation, it is a match
	- if any interest matches the search term, it should be considered a result, e.g. searching "bow" should yield people with interests in "bow hunting", "bowling", or "formal bowing"
	- if a search term is left blank it should not affect the results of the search
	- leading and trailing spaces of search terms should be ignored
	*/
	
	let result = students;
	// case 1: all non blank
	if(nameValue && majorValue && interestValue) {
		result = students.filter(student => {
			let fullName = student.name.first + ' ' + student.name.last;
			return student.interests.some(interest => {
				return (interest.toLowerCase().includes(interestValue)
				&& fullName.toLowerCase().includes(nameValue) 
				&& student.major.toLowerCase().includes(majorValue));
			});
		});
	}
	// case 2: name and major not blank
	else if(nameValue && majorValue) {
		result = students.filter(student => {
			let fullName = student.name.first + ' ' + student.name.last;
			return (fullName.toLowerCase().includes(nameValue) 
			&& student.major.toLowerCase().includes(majorValue));
		});
	}
	// case 3: name and interest not blank
	else if(nameValue && interestValue) {
		result = students.filter(student => {
			let fullName = student.name.first + ' ' + student.name.last;
			return student.interests.some(interest => {
				return (interest.toLowerCase().includes(interestValue) 
				&& fullName.toLowerCase().includes(nameValue));
			});
		});
	}
	// case 4: major and interest not blank
	else if(majorValue && interestValue) {
		result = students.filter(student => {
			return student.interests.some(interest => {
				return (interest.toLowerCase().includes(interestValue) 
				&& student.major.toLowerCase().includes(majorValue));
			});
		});
	}
	// case 5: name not blank
	else if (nameValue) {
		result = students.filter(student => {
			let fullName = student.name.first + ' ' + student.name.last;
			return fullName.toLowerCase().includes(nameValue);
		});
	}
	// case 6: major not blank
	else if(majorValue) {
		result = students.filter(student => {
			return student.major.toLowerCase().includes(majorValue);
		});
	}
	// case 7: interest not blank
	else if (interestValue) {
		result = students.filter(student => {
			return student.interests.some(interest => {
				return interest.toLowerCase().includes(interestValue);
			});
		});
	}
	// case 8: all blank or whitespace
	// console.log(result);
	document.getElementById("students").innerHTML = '';
	buildStudents(result);
}

document.getElementById("search-btn").addEventListener("click", handleSearch);