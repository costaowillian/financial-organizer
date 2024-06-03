import 'bootswatch/dist/litera/bootstrap.css';
import './custom.css'
import Router from '../main/routes'
import NavBar from '../components/navbar';

function App() {
  return (<>
    <div className="container">
      <Router />
    </div>

  </>);
}

export default App;
