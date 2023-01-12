import styles from './FeedItem.module.css';
import PropTypes from 'prop-types';
import { BlueSubject } from '../Subject';
import { MdStar, MdStarHalf, MdStarOutline } from 'react-icons/md';

const FeedItem = ({ name, userStatus, school, bio, subjects, rate, img }) => {
  return (
    <div className={styles.container}>
      <img className={styles.img} alt="프로필 이미지" src={img} />
      <div className={styles.nameAndStars}>
        <div className={styles.name}>
          {userStatus === 'TUTOR' ? `${name} 튜터` : `${name} 튜티`}
        </div>
        {userStatus === 'TUTOR' && (
          <div className={styles.stars}>
            {new Array(5).fill('star').map((_, count) => {
              const star = Number(rate);
              const checkedStar = (star) => {
                if (count + 1 - 0.5 === star) return 'half';
                else if (count + 1 > star) return 'dimmed';
                else return 'colored';
              };

              const renderStar = (color) => {
                switch (color) {
                  case 'dimmed':
                    return (
                      <MdStarOutline key={count} className={styles.emptyStar} />
                    );
                  case 'half':
                    return (
                      <MdStarHalf key={count} className={styles.halfStar} />
                    );
                  default:
                    return <MdStar key={count} className={styles.yellowStar} />;
                }
              };
              return renderStar(checkedStar(star));
            })}
          </div>
        )}
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
  userStatus: PropTypes.string,
  bio: PropTypes.string,
  school: PropTypes.string,
  subjects: PropTypes.array,
  rate: PropTypes.number,
  img: PropTypes.string,
};

export default FeedItem;
