var AppContext = React.createContext();

function makeAjaxCall(method, url, body) {
	var options = {method, headers:{'Content-Type':'application/json',accept:'application/json'}};
	if(!['GET','DELETE'].includes(method)) {
		options['body'] = JSON.stringify(body);
	}
	return fetch(url, options).then(res=>res.json())
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
		this.state = {username:'', password:''};
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
		makeAjaxCall('POST','/login', {username: this.state.username, password:this.state.password}).then((res)=> {
			console.log(res);
			if(res) {
				console.log('vrai');
				context.setToken('Basic ' + btoa(this.state.username + ':' + this.state.password));
			}
		})
	}

	render() {
		// console.log('icic');
		return <AppContext.Consumer>{(context)=>(
			<form onSubmit={(evt)=>this.handleSubmit(evt,context)}>
				<p key="up">username <input type="text" onChange={this.handleUsernameChange} required value={this.state.username} placeholder="Sachin" /></p>
				<p key="pp">password <input type="password" onChange={this.handlePasswordChange} required value={this.state.password}  /></p>
				<button key="b" type="submit">Login</button>
			</form>
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

class UserList extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users:[]};
	}
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
			}
		};
		console.log(this.state);
	}

	render() {
		let child = (this.state.token)?<BookList/>:<LoginForm/>;
		console.log(this.state.token);
		return <AppContext.Provider value={this.state}> 
			{ child }
		</AppContext.Provider>
	}
}


ReactDOM.render(<App/>, document.querySelector('main'));
