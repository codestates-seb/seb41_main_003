import styles from '../../pages/TuteeList.module.css';
import PropType from 'prop-types';

const MenuButtons = ({ subjectMenu, subjectHandler }) => {
  return (
    <div className={styles.menu}>
      <button
        value="국어"
        className={
          subjectMenu.includes('국어') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={subjectHandler}
      >
        국어
      </button>
      <button
        value="수학"
        className={
          subjectMenu.includes('수학') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={subjectHandler}
      >
        수학
      </button>
      <button
        value="사회"
        className={
          subjectMenu.includes('사회') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={subjectHandler}
      >
        사회
      </button>
      <button
        value="과학"
        className={
          subjectMenu.includes('과학') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={subjectHandler}
      >
        과학
      </button>
      <button
        value="영어"
        className={
          subjectMenu.includes('영어') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={subjectHandler}
      >
        영어
      </button>
      <button
        value="자격증"
        className={
          subjectMenu.includes('자격증')
            ? styles.activeMenu
            : styles.defaultMenu
        }
        onClick={subjectHandler}
      >
        자격증
      </button>
      <button
        value="기타"
        className={
          subjectMenu.includes('기타') ? styles.activeMenu : styles.defaultMenu
        }
        onClick={subjectHandler}
      >
        기타
      </button>
    </div>
  );
};

MenuButtons.propTypes = {
  subjectMenu: PropType.array,
  subjectHandler: PropType.func,
};

export default MenuButtons;
