import { MdStar, MdStarOutline } from 'react-icons/md';
import PropTypes from 'prop-types';
import styles from './StarRating.module.css';

const DetailStar = ({ rate }) => {
  return (
    <div>
      {new Array(5).fill('star').map((_, count) => {
        const checkStar = count + 1 > rate ? 'dimmed' : 'colored';
        console.log(checkStar);

        const renderStar = (color) => {
          switch (color) {
            case 'dimmed':
              return <MdStarOutline key={count} className={styles.emptyStar} />;
            default:
              return <MdStar key={count} className={styles.yellowStar} />;
          }
        };
        return renderStar(checkStar);
      })}
    </div>
  );
};

DetailStar.propTypes = {
  rate: PropTypes.number,
};

export default DetailStar;
