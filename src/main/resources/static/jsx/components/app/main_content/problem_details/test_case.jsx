import React from 'react';

class TestCase extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      problemId: this.props.problemId,
      testCaseInputText: '',
      resultText: 'Output\n---------------\n'
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(e) {
    this.setState({
      testCaseInputText: e.target.value
    });
  }

  handleSubmit(e) {
    e.preventDefault();
    this.setState({
      resultText: 'Output\n---------------\nLoading...'
    }, () => {
      const problemId = this.state.problemId;
      const testCaseInput = this.state.testCaseInputText
      const parameterArray = testCaseInput.split('\n');

      const testCaseData = JSON.stringify({
        id: problemId,
        parameterValues: parameterArray
      });

      fetch('/api/run', {
        method: 'post',
        headers: new Headers({
          'content-type': 'application/json'
        }),
        body: testCaseData
      }).then(response => {
        return response.json();
      }).then(data => {
        this.setState({
          resultText: 'Output\n---------------\n'+data.resultString
        });
      });
    })
  }

  componentWillReceiveProps(newProps) {
    if (this.state.problemId !== newProps.problemId) {
      this.setState({
        problemId: newProps.problemId,
        testCaseInputText: '',
        resultText: 'Output\n---------------\n'
      });
    }
  }

  render() {
    return (
      <div className='mjnb-test-case'>
        <div id='test-case-input'>
          <p>Custom Input </p>
          <i className='fa fa-question-circle' aria-hidden='true' />
          <div className='mjnb-test-case-popup'>
            Separate parameters by line and enclose arrays in []. e.g. ["foo", "bar"]
          </div>
          <textarea
              className='mjnb-custom-input'
              rows={ 4 }
              value={ this.state.testCaseInputText }
              onChange={ this.handleInputChange }
          />
          <button className='test-case-button' onClick={ this.handleSubmit }>
            Run Solution
          </button>
        </div>
        <div id='test-case-output'>
          <p>Result</p>
          <textarea
              id='output-container'
              readOnly
              rows={ 4 }
              value={ this.state.resultText }
          />
        </div>
      </div>
    );
  }
}

export default TestCase;
