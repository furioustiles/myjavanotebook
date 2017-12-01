import React from 'react';

class NewProblemForm extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      displayName: '',
      tagString: '',
      problemSourceURL: '',
      returnType: '',
      mainFunctionSignature: ''
    };

    this.updateInput = this.updateInput.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  isDisplayNameValid() {
    return this.state.displayName !== '';
  }

  isTagStringValid() {
    return true;
  }

  isProblemSourceURLValid() {
    return true;
  }

  isReturnTypeValid() {
    const validReturnTypes = ['int', 'int[]', 'int[][]', 'double', 'double[]', 'double[][]',
        'long', 'long[]', 'long[][]', 'char', 'char[]', 'char[][]', 'boolean', 'boolean[]',
        'boolean[][]', 'String', 'String[]', 'String[][]', 'ListNode', 'TreeNode'];
    const validReturnTypeSet = new Set(validReturnTypes);
    return validReturnTypeSet.has(this.state.returnType);
  }

  isMainFunctionSignatureValid() {
    return true;
  }

  isInputValid() {
    if (!this.isDisplayNameValid()) {
      return 1;
    } else if (!this.isTagStringValid()) {
      return 2;
    } else if (!this.isProblemSourceURLValid()) {
      return 3;
    } else if (!this.isReturnTypeValid()) {
      return 4;
    } else if (!this.isMainFunctionSignatureValid()) {
      return 5;
    }

    return 0;
  }

  updateInput(e) {
    const name = e.target.name;
    const value = e.target.value;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(e) {
    e.preventDefault();

    const inputValidity = this.isInputValid();

    const newProblemFormData = JSON.stringify({
      displayName: this.state.displayName,
      tagString: this.state.tagString,
      problemSourceURL: this.state.problemSourceURL,
      returnType: this.state.returnType,
      mainFunctionSignature: this.state.mainFunctionSignature
    });

    fetch('/api/problem/new', {
      method: 'post',
      headers: new Headers({
        'content-type': 'application/json'
      }),
      body: newProblemFormData
    }).then(() => {
      this.props.getAllProblems();
    }).then(() => {
      fetch('/api/problem/newest', {
        method: 'get'
      }).then(response => {
        return response.json();
      }).then(data => {
        this.props.selectProblem(data.fileName);
      });
    });
  }

  renderTextInput(inputName, inputValue, inputPlaceholder, shouldAutofocus) {
    if (shouldAutofocus) {
      return <input autoFocus className='new-problem-input' type='text'
          name={ inputName }
          value={ inputValue }
          placeholder={ inputPlaceholder }
          onChange={ this.updateInput }
      />;
    }

    return <input className='new-problem-input' type='text'
        name={ inputName }
        value={ inputValue }
        placeholder={ inputPlaceholder }
        onChange={ this.updateInput }
    />;
  }

  render() {
    return (
      <div id='mjnb-new-problem'>
        <form className='new-problem-form' onSubmit={ this.handleSubmit }>
          { this.renderTextInput('displayName', this.state.displayName,
                'Name (e.g. Foo Bar)',
                true) }
          <br />
          { this.renderTextInput('tagString', this.state.tagString,
                'Tags separated by comma (e.g. greedy, dynamic programming)',
                false) }
          <br />
          { this.renderTextInput('problemSourceURL', this.state.problemSourceURL,
                'Problem source (e.g. https://leetcode.com/problems/foobar)',
                false) }
          <br />
          { this.renderTextInput('returnType', this.state.returnType,
                'Return type (e.g. int, String[])',
                false) }
          <br />
          { this.renderTextInput('mainFunctionSignature', this.state.mainFunctionSignature,
                'Main function name (e.g. fooBar)',
                false) }
          <br />
          <input className='new-problem-submit' type='submit' value='Create' />
        </form>
      </div>
    );
  }
}

export default NewProblemForm;
