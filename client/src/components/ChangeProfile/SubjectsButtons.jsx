import styles from './ChangeProfileCard.module.css';
import PropType from 'prop-types';

const SubjectsButtons = ({ subjectTitles, setUser }) => {
  const btnArray = ['영어', '수학', '국어', '사회', '과학', '자격증', '기타'];

  const subjectHandler = (e) => {
    const { name } = e.target;
    if (subjectTitles.includes(name)) {
      setUser((prev) => {
        return {
          ...prev,
          subjects: prev.subjects.filter((obj) => obj.subjectTitle !== name),
        };
      });
    } else {
      setUser((prev) => {
        return {
          ...prev,
          subjects: [
            ...prev.subjects,
            {
              subjectId: btnArray.indexOf(name) + 1,
              subjectTitle: name,
              content: '',
            },
          ],
        };
      });
    }
  };

  return (
    <div className={styles.btnContain}>
      {btnArray.map((btnName, i) => {
        return (
          <button
            type="button"
            name={btnName}
            className={subjectTitles.includes(btnName) ? styles.active : ''}
            onClick={subjectHandler}
            key={i + 1}
          >
            {btnName}
          </button>
        );
      })}
    </div>
  );
};

SubjectsButtons.propTypes = {
  subjectTitles: PropType.array,
  setUser: PropType.func,
  setIsConfirm: PropType.func,
  setIsRequired: PropType.func,
};

export default SubjectsButtons;
