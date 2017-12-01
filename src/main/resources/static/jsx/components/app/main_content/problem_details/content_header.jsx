import React from 'react';

class ContentHeader extends React.Component {

  constructor(props) {
    super(props);
    this.state = {

    };

    this.handleDelete = this.handleDelete.bind(this);
  }

  handleDelete() {
    this.props.deleteProblem(this.props.problemId);
  }

  render() {
    return (
      <div id='problem-details-header'>
        <p className='problem-header-name'>{ this.props.displayName }</p>
        <p className='problem-header-date'>{ this.props.dateTimeString }</p>
        <div className='problem-header-buttons'>
          <a className='problem-header-link' href={ this.props.problemSourceURL } target='_blank' title='View problem source'>
            <i className='fa fa-link' aria-hidden='true' />
          </a>
          <a className='problem-header-link' onClick={ this.handleDelete } title='Delete problem'>
            <i className='fa fa-trash-o' aria-hidden='true' />
          </a>
        </div>
      </div>
    );
  }
}

export default ContentHeader;
