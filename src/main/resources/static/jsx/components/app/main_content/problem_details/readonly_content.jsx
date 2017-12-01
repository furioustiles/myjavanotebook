import React from 'react';
import ReadonlyCell from './readonly_content/readonly_cell.jsx';
import ReadonlyMenu from './readonly_content/readonly_menu.jsx';

class ReadonlyContent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      cells: this.props.cells,
      showCodeOnly: false
    };

    this.toggleShowCodeOnly = this.toggleShowCodeOnly.bind(this);
  }

  toggleShowCodeOnly() {
    this.setState({
      showCodeOnly: !this.state.showCodeOnly
    });
  }

  renderReadonlyCell(cell) {
    if (cell.cellType === 'CODE' || (cell.cellType === 'MARKDOWN' && !this.state.showCodeOnly)) {
      return <ReadonlyCell
          key={ cell.id }
          id={ cell.id }
          type={ cell.cellType }
          content={ cell.cellContent }
        />;
    }
  }

  componentWillMount() {

  }

  componentWillReceiveProps(newProps) {
    this.setState({
      cells: newProps.cells,
      showCodeOnly: false
    });
  }

  render() {
    return (
      <div id='mjnb-readonly-content' className='mjnb-content'>
        <div id='mjnb-readonly-cells' className='mjnb-cells'>
          <div className='cell-list'>
            {
              this.state.cells.map(cell => {
                return this.renderReadonlyCell(cell);
              })
            }
          </div>
        </div>
        <ReadonlyMenu
            showCodeOnly={ this.state.showCodeOnly }
            setEditMode={ this.props.setEditMode }
            toggleShowCodeOnly={ this.toggleShowCodeOnly }
        />
      </div>
    );
  }
}

export default ReadonlyContent;
