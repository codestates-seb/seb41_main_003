import styles from './DefaultModal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button';


const HandlerAlertModal = ({ text, modalHandler }) => {
  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>{text}</div>
      <ButtonNightBlue buttonHandler={modalHandler} text="확인" />
    </div>
  );
};

HandlerAlertModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

export default HandlerAlertModal;


