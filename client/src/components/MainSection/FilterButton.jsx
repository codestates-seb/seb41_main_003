import styles from '../../pages/TuteeList.module.css';
import PropType from 'prop-types';

const MenuButtons = ({ isClicked, clickHandler }) => {
  return (
    <div className={styles.menu}>
      <button
        value="국어"
        className={
          isClicked.includes('국어') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={clickHandler}
      >
        국어
      </button>
      <button
        value="수학"
        className={
          isClicked.includes('수학') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={clickHandler}
      >
        수학
      </button>
      <button
        value="사회"
        className={
          isClicked.includes('사회') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={clickHandler}
      >
        사회
      </button>
      <button
        value="과학"
        className={
          isClicked.includes('과학') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={clickHandler}
      >
        과학
      </button>
      <button
        value="영어"
        className={
          isClicked.includes('영어') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={clickHandler}
      >
        영어
      </button>
      <button
        value="자격증"
        className={
          isClicked.includes('자격증') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={clickHandler}
      >
        자격증
      </button>
      <button
        value="기타"
        className={
          isClicked.includes('기타') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={clickHandler}
      >
        기타
      </button>
    </div>
  );
};

MenuButtons.propTypes = {
  isClicked: PropType.array,
  clickHandler: PropType.func,
};

export default MenuButtons;
