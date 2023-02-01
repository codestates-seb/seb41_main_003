import PropTypes from 'prop-types';
import styles from './MenuButton.module.css';

export const SnowMenuButton = ({ text, buttonHandler }) => {
  return (
    <button className={styles.snowButton} onClick={buttonHandler}>
      {text}
    </button>
  );
};

SnowMenuButton.propTypes = {
  text: PropTypes.string,
  buttonHandler: PropTypes.func,
};

export const NightMenuButton = ({ text, buttonHandler }) => {
  return (
    <button className={styles.nightButton} onClick={buttonHandler}>
      {text}
    </button>
  );
};

NightMenuButton.propTypes = {
  text: PropTypes.string,
  buttonHandler: PropTypes.func,
};
