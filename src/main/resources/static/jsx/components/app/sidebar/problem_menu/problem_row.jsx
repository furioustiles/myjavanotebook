import React from 'react';

class ProblemRow extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      fileName: '',
      displayName: '',
      tags: [],
    };

    this.handleView = this.handleView.bind(this);
  }

  handleView(e) {
    this.props.selectProblem(this.state.fileName);
  }

  componentWillMount() {
    this.setState({
      fileName: this.props.problemData.fileName,
      displayName: this.props.problemData.displayName,
      tags: this.props.problemData.tags,
    });
  }

  render() {
    const smallTagArray = this.state.tags.slice(0, 3);
    const problemTags = smallTagArray.join(', ');

    return (
      <li className='mjnb-problem-row' id={ this.state.fileName } onClick={ this.handleView }>
        <div className='problem-display-name-container'>
          <span>{ this.state.displayName }</span>
        </div>
        <div className='problem-tag-container'>
          <span>{ problemTags }</span>
        </div>
      </li>
    );
  }
}

export default ProblemRow;
