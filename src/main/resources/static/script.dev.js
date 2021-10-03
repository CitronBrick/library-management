var AppContext = React.createContext();

function makeAjaxCall(method, url, body) {
	var options = {method, headers:{'Content-Type':'application/json',accept:'application/json'}};
	if(!['GET','DELETE'].includes(method)) {
		options['body'] = JSON.stringify(body);
	}
	return fetch(url, options).then(res=>{console.log(res);;res.json();})
}

function BookDescription({book}) {
	return <div className="book">
		<h4>{book.title} by {book.author}</h4>
		<small>{book.availableNbCopies}/{book.totalNbCopies}</small>
	</div>;
}



class LoginForm extends React.Component {

	constructor(props) {
		super(props);
		this.state = {username:'', password:'', message: ''};
		this.handleUsernameChange = this.handleUsernameChange.bind(this);
		this.handlePasswordChange = this.handlePasswordChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleUsernameChange(evt) {
		this.setState({username: evt.target.value });
	}

	handlePasswordChange(evt) {
		this.setState({password: evt.target.value});
	}

	handleSubmit(evt,context) {
		evt.preventDefault();
		let body =  JSON.stringify({username: this.state.username, password:this.state.password});
		fetch('/login', {method:'POST', body, headers:{'Content-Type':'application/json'}}).then((res)=> {
			console.log(res.status);
			if(res.status == 200) {
				res.json().then((user)=>{
					console.log(user);
					context.setToken('Basic ' + btoa(this.state.username + ':' + this.state.password));
				});
			} else if(res.status == 404) {
				this.setState({message: 'username or password is wrong'});
			}
		})
	}

	render() {
		return <AppContext.Consumer>{(context)=>(
			<form onSubmit={(evt)=>this.handleSubmit(evt,context)}>
				<p key="up">username <input type="text" onChange={this.handleUsernameChange} required value={this.state.username} placeholder="Sachin" /></p>
				<p key="pp">password <input type="password" onChange={this.handlePasswordChange} required value={this.state.password}  /></p>
				<button key="b" type="submit">Login</button>
				<output>{this.state.message}</output>
			</form>
		)}</AppContext.Consumer>;
	}
}

class LogoutButton extends React.Component {

	constructor(props) {
		super(props);
	}

	handleClick(evt,context) {
		context.logout();
	}

	render() {
		return <AppContext.Consumer>{(context)=>(
			<button type="button" onClick={(evt)=>this.handleClick(evt,context)}>Logout</button>
		)}</AppContext.Consumer>;
	}
}


class BookList extends React.Component {

	constructor(props) {
		super(props);
		this.state = {books: []};
	}

	componentDidMount() {
		fetch("/books/all").then(res=>res.json()).then((books)=> {
			this.setState({books });
		});
	}

	render() {
		return <div className="bookList">
			{this.state.books.map((b,i)=>(<BookDescription key={b.id} book={b} />))}
		</div>;
	}
}

function UserList(props) {

	let context = React.useContext(AppContext);

	React.useEffect(()=>{
		console.log('gonaprintcontext componentDidMount ' + context.token);
		fetch("/users/all", {headers:{'Authorization':context.token}}).then(res=>res.json()).then(u=>{
			console.log(u);
		})
	},[]);

	return <div className="users"></div>;
}



class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			username: '', token: '',
			setToken: (token)=> {
				this.setState({token},()=> {
					console.log(this.state.token);
				});
			},
			logout: ()=>{
				this.setState({token:''});
			}
		};
		console.log(this.state);
	}

	render() {
		let child = (this.state.token)?<React.Fragment><LogoutButton/><BookList/><UserList/></React.Fragment>:<LoginForm/>;
		console.log(this.state.token);
		return <AppContext.Provider value={this.state}> 
			{ child }
		</AppContext.Provider>
	}
}


ReactDOM.render(<App/>, document.querySelector('main'));
