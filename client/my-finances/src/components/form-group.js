import React from "react";

class FormGroup extends React.Component {
    render() {
        return (<>
            <div className="form-group" style={{ marginBottom: '15px' }}>
                <label htmlFor={this.props.htmlFor}>{this.props.label}</label>
                {this.props.children}
            </div>
        </>)
    }
}

export default FormGroup;