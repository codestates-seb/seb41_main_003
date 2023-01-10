import styles from './FeedItem.module.css';
import PropTypes from 'prop-types';
import { BlueSubject } from '../Subject';
import { MdStar, MdStarHalf, MdStarOutline } from 'react-icons/md';

const FeedItem = ({ name, school, bio, subjects, rate, img }) => {
  const makeStars = (yellow, gray, half = 0) => {
    const arr = [];
    for (let i = 1; i <= yellow; i++) {
      arr.push('y');
    }
    if (half !== 0) {
      arr.push('h');
    }
    for (let i = 1; i <= gray; i++) {
      arr.push('g');
    }
    return arr;
  };

  const stars = Number.isInteger(Number(rate))
    ? makeStars(rate, 5 - rate)
    : makeStars(Math.floor(rate), 5 - (Math.floor(rate) + 1), 1);

  return (
    <div className={styles.container}>
      <img className={styles.img} alt="프로필 이미지" src={img} />
      <div className={styles.nameAndStars}>
        <div className={styles.name}>{`${name} 튜터`}</div>
        <div className={styles.stars}>
          {stars.map((el, index) => {
            if (el === 'y') {
              return <MdStar key={index} className={styles.yellowStar} />;
            } else if (el === 'g') {
              return <MdStarOutline key={index} className={styles.emptyStar} />;
            } else {
              return <MdStarHalf key={index} className={styles.halfStar} />;
            }
          })}
        </div>
      </div>
      <div className={styles.subjectBox}>
        {subjects.map((el) => (
          <div key={el.subjectId} className={styles.subject}>
            <BlueSubject text={el.subjectTitle} />
          </div>
        ))}
      </div>
      <span className={styles.school}>{school}</span>
      <div className={styles.bio}>
        {bio.length > 16 ? `${bio.slice(0, 16)}...` : bio}
      </div>
    </div>
  );
};

FeedItem.propTypes = {
  name: PropTypes.string,
  bio: PropTypes.string,
  school: PropTypes.string,
  subjects: PropTypes.array,
  rate: PropTypes.number,
  img: PropTypes.string,
};

export default FeedItem;
