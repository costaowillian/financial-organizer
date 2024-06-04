import 'bootswatch/dist/litera/bootstrap.css';
import './custom.css'
import Router from '../main/routes'

import 'toastr/build/toastr.css';
import 'toastr/build/toastr.min.js';

function App() {
  return (<>
    <div className="container">
      <Router />
    </div>

  </>);
}

export default App;
