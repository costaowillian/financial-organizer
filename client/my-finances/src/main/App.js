import 'bootswatch/dist/litera/bootstrap.css';
import './custom.css'
import Router from '../main/routes'

import 'toastr/build/toastr.css';
import 'toastr/build/toastr.min.js';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';

import { AuthProvider } from '../context/authContex';

function App() {
  return (<>
    <AuthProvider>
      <div className="container">
        <Router />
      </div>
    </AuthProvider>
  </>);
}

export default App;
