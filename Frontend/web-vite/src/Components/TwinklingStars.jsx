import React, { useRef, useEffect } from 'react';

const TwinklingStars = ({ width, height}) => {
    const canvasRef = useRef(null);

    useEffect(() => {
        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');
        const stars = createStars(1000, canvas.width, canvas.height);

        let animationFrameId;

        // Function to update the stars' properties
        const updateStars = () => {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            drawStars(ctx, stars);
            stars.forEach(star => {
                star.twinkle();
            });
            animationFrameId = requestAnimationFrame(updateStars);
        };

        // Start the animation
        updateStars();

        // Clean up
        return () => {
            cancelAnimationFrame(animationFrameId);
        };
    }, []);

    // Function to create stars
    const createStars = (count, width, height) => {
        let stars = [];
        for (let i = 0; i < count; i++) {
            stars.push(new Star(Math.random() * width, Math.random() * height));
        }
        return stars;
    };

    // Function to draw stars
    const drawStars = (ctx, stars) => {
        stars.forEach(star => {
            ctx.beginPath();
            ctx.arc(star.x, star.y, star.radius, 0, Math.PI);
            ctx.fillStyle = `rgba(25,118,210, ${star.opacity})`;
            ctx.fill();
        });
    };

    // Star class
    class Star {
        constructor(x, y) {
            this.x = x;
            this.y = y;
            this.radius = 1.1 * Math.random();
            this.opacity = 1;
            this.increment = Math.random() * 0.02;
        }

        twinkle() {
            this.opacity += this.increment;
            if (this.opacity > 1 || this.opacity < 0) {
                this.increment = -this.increment;
            }
        }
    }

    return <canvas ref={canvasRef} style={{
        width: '100vw',
        height: '100vh',
    }} />;
};

export default TwinklingStars;
