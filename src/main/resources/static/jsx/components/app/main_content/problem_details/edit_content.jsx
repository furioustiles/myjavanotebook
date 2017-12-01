import React from 'react';
import EditCell from './edit_content/edit_cell.jsx';
import EditMenu from './edit_content/edit_menu.jsx';

class EditContent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      problemId: this.props.problemId,
      cells: this.props.cells,
      focusedCell: this.props.focusedCell,
      focusedCellIndex: this.props.focusedCellIndex
    };
  }

  renderEditCell(cell) {
    return <EditCell
        key={ cell.id }
        id={ cell.id }
        type={ cell.cellType }
        content={ cell.cellContent }
        focusedCell={ this.state.focusedCell }
        focusedCellIndex={ this.state.focusedCellIndex }
        updateCell={ this.props.updateCell }
        changeFocusedCell={ this.props.changeFocusedCell }
    />;
  }

  componentWillReceiveProps(newProps) {
    this.setState({
      problemId: newProps.problemId,
      cells: newProps.cells,
      focusedCell: newProps.focusedCell,
      focusedCellIndex: newProps.focusedCellIndex
    });
  }

  render() {
    return (
      <div id='mjnb-edit-content' className='mjnb-content'>
        <div id='mjnb-edit-cells' className='mjnb-cells'>
          <div className='cell-list edit-cell-list'>
            {
              this.state.cells.map(cell => {
                return this.renderEditCell(cell);
              })
            }
          </div>
        </div>
        <EditMenu
            setReadonlyMode={ this.props.setReadonlyMode }
            insertCell={ this.props.insertCell }
            removeCell={ this.props.removeCell }
            moveCellUp={ this.props.moveCellUp }
            moveCellDown={ this.props.moveCellDown }
        />
      </div>
    );
  }
}

export default EditContent;
