import React from 'react';

class SidebarHeader extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      searchInput: ''
    };

    this.handleUpload = this.handleUpload.bind(this);
    this.handleNewProblemClick = this.handleNewProblemClick.bind(this);
    this.handleSearchSubmit = this.handleSearchSubmit.bind(this);
    this.updateInput = this.updateInput.bind(this);
  }

  handleUpload(e) {
    let files = e.target.files;
    let reader = new FileReader();
    let fileString;
    let self = this;

    reader.onload = (e) => {
      fileString = encodeURIComponent(reader.result);
      fetch('/api/content/upload?fileString='+fileString, {
        method: 'post'
      }).then(() => {
        console.log(fileString);
        self.props.getAllProblems();
        fetch('/api/problem/newest', {
          method: 'get'
        }).then(response => {
          return response.json();
        }).then(data => {
          self.props.selectProblem(data.fileName);
        });
      });
    }

    reader.readAsText(files[0]);
  }

  handleNewProblemClick(e) {
    this.props.selectProblem('new');
  }

  handleSearchSubmit(e) {
    e.preventDefault();
    this.props.getProblemsByTag(this.state.searchInput);
  }

  updateInput(e) {
    this.setState({
      searchInput: e.target.value
    })
  }

  render() {
    const sidebarTop = (
      <div id='sidebar-top'>
        <div id='sidebar-brand'>
          MyJavaNotebook
        </div>
        <div id='sidebar-button-wrapper'>
          <div className='sidebar-button'>
            <a role='button'>
              <label htmlFor='mjnb-file' className='mjnb-file-label'>
                <i className='fa fa-upload mjnb-menu-icon' aria-hidden='true' title='Upload problem' />
              </label>
              <input
                  id='mjnb-file'
                  type='file'
                  onChange={ this.handleUpload }
              />
            </a>
          </div>
          <div className='sidebar-button'>
            <a role='button' onClick={ this.handleNewProblemClick }>
              <i className='fa fa-plus mjnb-menu-icon' aria-hidden='true' title='New problem' />
            </a>
          </div>
        </div>
      </div>
    );

    const sidebarSearch = (
      <div id='sidebar-search-wrapper'>
        <div id='sidebar-search'>
          <form id='mjnb-search-form' onSubmit={ this.handleSearchSubmit }>
            <i className='fa fa-search' aria-hidden='true' />
            <input id='sidebar-search-input' type='text' name='searchInput'
                placeholder='Search by tag'
                value={ this.state.searchInput }
                onChange={ this.updateInput }
            />
          </form>
        </div>
      </div>
    );

    return (
      <div id='sidebar-header'>
        { sidebarTop }
        { sidebarSearch }
      </div>
    );
  }
}

export default SidebarHeader;
