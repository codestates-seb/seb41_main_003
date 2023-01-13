import styles from './DropDown.module.css';
import { useState } from 'react';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { MdMenu } from 'react-icons/md';
const DropDown = () => {
  const [isClicked, setIsClicked] = useState(false);
  const blueButtonHandler = () => {};
  const redButtonHandler = () => {};
  return (
    <div className={styles.dropDownButton}>
      <button
        id="pp"
        onBlur={() => {
          setIsClicked(false);
        }}
        onClick={() => setIsClicked(!isClicked)}
      >
        <div className={styles.buttonImg}>
          <MdMenu size="24px" />
        </div>
      </button>

      {isClicked && (
        <ul>
          <li>
            <ButtonNightBlue
              text="일지 수정"
              buttonHandler={blueButtonHandler}
            />
          </li>
          <li>
            <ButtonRed text="일지 삭제" buttonHandler={redButtonHandler} />
          </li>
        </ul>
      )}
    </div>
  );
};

export default DropDown;
